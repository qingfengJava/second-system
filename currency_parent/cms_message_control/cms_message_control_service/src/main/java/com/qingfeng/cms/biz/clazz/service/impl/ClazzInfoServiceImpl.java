package com.qingfeng.cms.biz.clazz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.clazz.dao.ClazzInfoDao;
import com.qingfeng.cms.biz.clazz.service.ClazzInfoService;
import com.qingfeng.cms.domain.clazz.dto.ClazzInfoSaveDTO;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 班级信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-12 22:37:25
 */
@Service
public class ClazzInfoServiceImpl extends ServiceImpl<ClazzInfoDao, ClazzInfoEntity> implements ClazzInfoService {

    private static final Logger log1 = LoggerFactory.getLogger(ClazzInfoServiceImpl.class);
    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存班级信息
     * @param clazzInfoSaveDTO
     */
    @Override
    public void saveClazzInfo(ClazzInfoSaveDTO clazzInfoSaveDTO, Long userId) {
        if (ObjectUtil.isNotEmpty(clazzInfoSaveDTO.getId())) {
            // 更新操作
            baseMapper.updateById(dozerUtils.map2(clazzInfoSaveDTO, ClazzInfoEntity.class));
        } else {
            // 保存操作
            ClazzInfoEntity clazzInfoEntity = baseMapper.selectOne(Wraps.lbQ(new ClazzInfoEntity())
                    .eq(ClazzInfoEntity::getUserId, userId));
            if (ObjectUtil.isEmpty(clazzInfoEntity)) {
                ClazzInfoEntity clazzInfo = dozerUtils.map2(clazzInfoSaveDTO, ClazzInfoEntity.class);
                clazzInfo.setUserId(userId);
                baseMapper.insert(clazzInfo);
            }
        }
    }
}