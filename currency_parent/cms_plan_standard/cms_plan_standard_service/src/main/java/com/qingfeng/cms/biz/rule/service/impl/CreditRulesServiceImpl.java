package com.qingfeng.cms.biz.rule.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.biz.rule.dao.CreditRulesDao;
import com.qingfeng.cms.biz.rule.enums.CreditRulesExceptionMsg;
import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.rule.dto.CreditRulesCheckDTO;
import com.qingfeng.cms.domain.rule.dto.CreditRulesSaveDTO;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.rule.enums.RuleCheckEnum;
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
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 加分（学分）细则表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Service("creditRulesService")
@Slf4j
public class CreditRulesServiceImpl extends ServiceImpl<CreditRulesDao, CreditRulesEntity> implements CreditRulesService {

    private static Long USER_ID = 0L;
    private static final String RULES_KEY = "rules.inform.email";

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private LevelService levelService;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private EmailApi emailApi;
    @Autowired
    private NewsNotifyApi newsNotifyApi;

    @Autowired
    private RabbitSendMsg rabbitSendMsg;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 保存学分细则信息
     *
     * @param creditRulesSaveDTOList
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveCreditRules(List<CreditRulesSaveDTO> creditRulesSaveDTOList, Long userId) {
        List<CreditRulesEntity> creditRulesEntityList = dozerUtils.mapList(creditRulesSaveDTOList, CreditRulesEntity.class);
        //如果有等级不能存在相同的等级
        if (creditRulesEntityList.size() > 1) {
            //先按等级进行分组
            creditRulesEntityList.stream()
                    .collect(Collectors.groupingBy(
                            CreditRulesEntity::getLevelId
                    )).forEach((index, ruleList) -> {
                        //对每个value进行分组
                        ruleList.stream()
                                .collect(Collectors.groupingBy(
                                        (c -> Optional.ofNullable(c.getScoreGrade()).orElse("null")),
                                        Collectors.counting()
                                )).forEach((k, v) -> {
                                    if (!"null".equals(k) && v > 1) {
                                        throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.GRADE_IS_SAME.getMsg());
                                    }
                                });
                    });
        }

        //删除原有的学分，再重新进行添加
        baseMapper.delete(Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesEntityList.get(0).getLevelId()));

        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            creditRulesEntityList.forEach(c -> c.setIsCheck(RuleCheckEnum.INIT));
        } else {
            creditRulesEntityList.forEach(c -> c.setIsCheck(RuleCheckEnum.IS_FINISHED));
        }

        this.saveBatch(creditRulesEntityList);

    }

    /**
     * 根据学分Id删除学分和其对应的等级信息
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void removeLevelById(Long id) {
        //首先查询学分详情
        CreditRulesEntity creditRulesEntity = baseMapper.selectById(id);
        //查询对应的等级下面是否对应多个学分，一般是只有一个的
        List<CreditRulesEntity> creditRulesEntityList = baseMapper.selectList(Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesEntity.getLevelId()));
        if (creditRulesEntityList.size() == 1) {
            //说明只有一条记录，连同等级直接删除
            levelService.removeById(creditRulesEntity.getLevelId());
        }
        baseMapper.deleteById(id);
    }

    /**
     * 学分细则审核
     *
     * @param creditRulesCheckDTO
     */
    @Override
    public void checkRule(CreditRulesCheckDTO creditRulesCheckDTO, Long userId) throws JsonProcessingException {
        USER_ID = userId;
        CreditRulesEntity creditRulesEntity = dozerUtils.map2(creditRulesCheckDTO, CreditRulesEntity.class);
        baseMapper.updateById(creditRulesEntity);
        //查询详情
        CreditRulesEntity rules = baseMapper.selectById(creditRulesCheckDTO.getId());
        //查询关联的等级
        LevelEntity level = levelService.getById(rules.getLevelId());
        //查询关联的用户信息
        User user = null;
        if (rules.getCreateUser() != 0) {
            user = userApi.get(rules.getCreateUser()).getData();
        }

        if (ObjectUtil.isNotEmpty(user)) {
            // 审核结果发送消息通知  目前先发送邮件通知
            if (ObjectUtil.isNotEmpty(user.getEmail())) {
                String title = creditRulesCheckDTO.getIsCheck().equals(RuleCheckEnum.IS_FINISHED) ?
                        "项目等级<" + level.getLevelContent() + ">关联的学分申请审核通过通知" :
                        "项目等级<" + level.getLevelContent() + ">关联的学分审核不通过通知";

                //有邮箱就先向邮箱中发送消息   使用消息队列进行发送  失败重试三次
                rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(EmailEntity.builder()
                        .email(user.getEmail())
                        .title(title)
                        .body(creditRulesCheckDTO.getCheckDetail())
                        .key(RULES_KEY)
                        .build()), RULES_KEY);

                //先将消息通知写入数据库
                R r = newsNotifyApi.save(NewsNotifySaveDTO.builder()
                        .userId(rules.getCreateUser())
                        .newsType(NewsTypeEnum.MAILBOX)
                        .newsTitle(title)
                        .newsContent(creditRulesCheckDTO.getCheckDetail())
                        .isSee(IsSeeEnum.IS_NOT_VIEWED)
                        .build());

                if (r.getIsError()) {
                    throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.NEWS_SAVE_FAILED.getMsg());
                }


            } else if (ObjectUtil.isNotEmpty(user.getMobile())) {
                // TODO 向短信发送信息 待完善

            }
        } else {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.USER_NOT_EXITS.getMsg());
        }
    }

    /**
     * 进入死信队列的消息，向自己发送一下通知
     * @param emailEntity
     */
    @Override
    public void sendMessageToSlfe(EmailEntity emailEntity) {
        //先根据Id查询自己的信息
        User user = userApi.get(USER_ID).getData();
        if (ObjectUtil.isNotEmpty(user)) {
            if (ObjectUtil.isNotEmpty(user.getEmail())) {
                //邮箱不为空 就给邮箱发送信息  这里操作不用失败重试需要重新封装信息
                emailEntity.setEmail(user.getEmail())
                        .setBody("关于" + emailEntity.getTitle() + "邮件通知失败！，如有必要，请线下通知对方。")
                        .setTitle("消息通知失败");
                emailApi.sendEmail(emailEntity);
            } else if (ObjectUtil.isNotEmpty(user.getMobile())) {
                // TODO 否则，使用短信通知
            }
        }
    }

    @Deprecated
    private void checkCreditRules(CreditRulesEntity creditRulesEntity) {
        // 检查条件，当前项目下是否已经学分了，若分等级，同一等级下是否已经有学分了
        LbqWrapper<CreditRulesEntity> wrapper = Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesEntity.getLevelId());
        if (ObjectUtil.isNotEmpty(creditRulesEntity.getScoreGrade())) {
            //有等级划分
            wrapper.eq(CreditRulesEntity::getScoreGrade, creditRulesEntity.getScoreGrade());
        }
        CreditRulesEntity creditRules = baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNotEmpty(creditRules)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}