package com.qingfeng.cms.biz.level.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.level.dao.LevelDao;
import com.qingfeng.cms.biz.level.enums.LevelExceptionMsg;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.dto.LevelUpdateDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private RoleApi roleApi;

    /**
     * 保存项目等级信息
     *
     * @param levelSaveDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveLevel(LevelSaveDTO levelSaveDTO, Long userId) {
        //保存前，先查询是否已经存在
        LevelEntity levelEntity = dozerUtils.map2(levelSaveDTO, LevelEntity.class);
        checkLevel(levelEntity);

        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            levelEntity.setIsCheck(LevelCheckEnum.INIT);
        } else {
            levelEntity.setIsCheck(LevelCheckEnum.IS_FINISHED);
        }

        // 进行保存
        baseMapper.insert(levelEntity);
    }

    /**
     * 根据id修改项目等级信息
     * @param levelUpdateDTO
     * @param userId
     */
    @Override
    public void updateLevelById(LevelUpdateDTO levelUpdateDTO, Long userId) {
        LevelEntity levelEntity = dozerUtils.map2(levelUpdateDTO, LevelEntity.class);
        checkLevel(levelEntity);
        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            levelEntity.setIsCheck(LevelCheckEnum.INIT);
        }

        baseMapper.updateById(levelEntity);
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
        if (ObjectUtil.isNotEmpty(levelEntity.getId())){
            wrapper.ne(LevelEntity::getId, levelEntity.getId());
        }

        LevelEntity level = baseMapper.selectOne(wrapper);

        if (ObjectUtil.isNotEmpty(level)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), LevelExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}