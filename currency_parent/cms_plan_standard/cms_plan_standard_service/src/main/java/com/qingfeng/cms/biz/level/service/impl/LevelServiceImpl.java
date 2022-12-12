package com.qingfeng.cms.biz.level.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.level.dao.LevelDao;
import com.qingfeng.cms.biz.level.enums.LevelExceptionMsg;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.level.dto.LevelCheckDTO;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.dto.LevelUpdateDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.sms.email.EmailApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目等级表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Service("levelService")
@Slf4j
public class LevelServiceImpl extends ServiceImpl<LevelDao, LevelEntity> implements LevelService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private CreditRulesService creditRulesService;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private EmailApi emailApi;
    @Autowired
    private NewsNotifyApi newsNotifyApi;

    /**
     * 保存项目等级信息
     *
     * @param levelSaveDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public List<LevelEntity> saveLevel(List<LevelSaveDTO> levelSaveDTO, Long userId) {
        //删除已有的数据（等级和学分），重新添加等级
        baseMapper.delete(Wraps.lbQ(new LevelEntity())
                .eq(LevelEntity::getProjectId, levelSaveDTO.get(0).getProjectId()));
        creditRulesService.remove(Wraps.lbQ(new CreditRulesEntity())
                .in(CreditRulesEntity::getLevelId, levelSaveDTO.stream()
                        .map(LevelSaveDTO::getProjectId)
                        .collect(Collectors.toList())));

        List<LevelEntity> levelEntityList = dozerUtils.mapList(levelSaveDTO, LevelEntity.class);

        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            levelEntityList.forEach(l -> l.setIsCheck(LevelCheckEnum.INIT));
        } else {
            levelEntityList.forEach(l -> l.setIsCheck(LevelCheckEnum.IS_FINISHED));
        }

        return levelEntityList.stream()
                .map(levelEntity -> {
                    checkLevel(levelEntity);
                    baseMapper.insert(levelEntity);
                    return levelEntity;
                }).collect(Collectors.toList());

        // TODO mysql并发插入操作导致死锁
        /*return levelEntityList.parallelStream()
                .peek(levelEntity -> {
                    checkLevel(levelEntity);
                    baseMapper.insert(levelEntity);
                })
                .collect(Collectors.toList());*/

        /*return levelEntityList.stream().map(levelEntity ->
                        // 进行保存 采用异步处理
                        CompletableFuture.supplyAsync(() -> {
                                    checkLevel(levelEntity);
                                    baseMapper.insert(levelEntity);
                                    return levelEntity;
                                }
                        ))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());*/
    }

    /**
     * 根据id修改项目等级信息
     *
     * @param levelUpdateDTO
     * @param userId
     */
    @Override
    public LevelEntity updateLevelById(LevelUpdateDTO levelUpdateDTO, Long userId) {
        LevelEntity levelEntity = dozerUtils.map2(levelUpdateDTO, LevelEntity.class);
        checkLevel(levelEntity);
        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            levelEntity.setIsCheck(LevelCheckEnum.INIT);
        }

        baseMapper.updateById(levelEntity);

        return levelEntity;
    }

    /**
     * 根据Id删除等级及其对应的学分
     * @param id
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void removeLevelById(Long id) {
        //先删除学分
        creditRulesService.remove(Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId,id));
        //删除等级
        baseMapper.deleteById(id);
    }

    /**
     * 审核项目等级
     * @param levelCheckDTO
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void checkLevel(LevelCheckDTO levelCheckDTO) {
        LevelEntity levelEntity = dozerUtils.map2(levelCheckDTO, LevelEntity.class);
        //先进行信息保存
        baseMapper.updateById(levelEntity);
        //查询等级详情
        LevelEntity level = baseMapper.selectById(levelCheckDTO.getId());
        //查询关联的用户信息的详情
        User user = null;
        if (level.getCreateUser() != 0){
            user = userApi.get(level.getCreateUser()).getData();
        }
        if (ObjectUtil.isNotEmpty(user)){
            // TODO 审核结果发送消息通知  目前先发送邮件通知
            if (ObjectUtil.isNotEmpty(user.getEmail())) {
                String title = levelCheckDTO.getIsCheck().equals(LevelCheckEnum.IS_FINISHED) ?
                        "项目等级<" + level.getLevelContent() + ">申请审核通过通知" :
                        "项目等级<" + level.getLevelContent() + ">审核不通过通知";

                //有邮箱就先向邮箱中发送消息
                Integer code = emailApi.sendEmail(EmailEntity.builder()
                        .email(user.getEmail())
                        .title(title)
                        .body(levelCheckDTO.getCheckDetail())
                        .build());
                if (code != 1) {
                    // TODO 目前先不做处理，后面使用消息队列做失败重试三次处理
                } else {
                    //先将消息通知写入数据库
                    R r = newsNotifyApi.save(NewsNotifySaveDTO.builder()
                            .userId(level.getCreateUser())
                            .newsType(NewsTypeEnum.MAILBOX)
                            .newsTitle(title)
                            .newsContent(levelCheckDTO.getCheckDetail())
                            .isSee(IsSeeEnum.IS_NOT_VIEWED)
                            .build());

                    if (r.getIsError()){
                        throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), LevelExceptionMsg.NEWS_SAVE_FAILED.getMsg());
                    }
                }

            } else if (ObjectUtil.isNotEmpty(user.getMobile())) {
                // TODO 向短信发送信息 待完善

            }
        }else {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), LevelExceptionMsg.USER_NOT_EXITS.getMsg());
        }
    }

    /**
     * 检查是否重复
     *
     * @param levelEntity
     */
    private void checkLevel(LevelEntity levelEntity) {
        LbqWrapper<LevelEntity> wrapper = Wraps.lbQ(new LevelEntity())
                .eq(LevelEntity::getProjectId, levelEntity.getProjectId())
                .like(LevelEntity::getLevelContent, levelEntity.getLevelContent());
        if (ObjectUtil.isNotEmpty(levelEntity.getId())) {
            wrapper.ne(LevelEntity::getId, levelEntity.getId());
        }

        LevelEntity level = baseMapper.selectOne(wrapper);

        if (ObjectUtil.isNotEmpty(level)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), LevelExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}