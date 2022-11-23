package com.qingfeng.cms.biz.level.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.level.dao.LevelDao;
import com.qingfeng.cms.biz.level.enums.LevelExceptionMsg;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
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
        checkLevel(levelSaveDTO);
        LevelEntity levelEntity = dozerUtils.map2(levelSaveDTO, LevelEntity.class);

        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.STU_OFFICE_ADMIN.name()});
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
     * 检查是否重复
     *
     * @param levelSaveDTO
     */
    private void checkLevel(LevelSaveDTO levelSaveDTO) {
        LevelEntity levelEntity = baseMapper.selectOne(Wraps.lbQ(new LevelEntity())
                .eq(LevelEntity::getProjectId, levelSaveDTO.getProjectId())
                .like(LevelEntity::getLevelContent, levelSaveDTO.getLevelContent()));

        if (ObjectUtil.isNotEmpty(levelEntity)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), LevelExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}