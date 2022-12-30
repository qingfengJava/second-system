package com.qingfeng.cms.biz.student.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.student.dao.StuInfoDao;
import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 学生信息详情表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class StuInfoServiceImpl extends ServiceImpl<StuInfoDao, StuInfoEntity> implements StuInfoService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存用户详情信息
     * @param stuInfoSaveDTO
     */
    @Override
    public void saveStuInfo(StuInfoSaveDTO stuInfoSaveDTO) {
        if (ObjectUtil.isNotEmpty(stuInfoSaveDTO.getId())){
            //用户维护的信息
            baseMapper.updateById(dozerUtils.map2(stuInfoSaveDTO, StuInfoEntity.class));
        }else{
            //首先要查询当前用户下是否有详情信息  有则不做处理
            StuInfoEntity stuInfo = baseMapper.selectOne(Wraps.lbQ(new StuInfoEntity())
                    .eq(StuInfoEntity::getUserId, stuInfoSaveDTO.getUserId()));
            if (ObjectUtil.isEmpty(stuInfo)){
                StuInfoEntity stuInfoEntity = dozerUtils.map2(stuInfoSaveDTO, StuInfoEntity.class);
                baseMapper.insert(stuInfoEntity);
            }
        }
    }
}