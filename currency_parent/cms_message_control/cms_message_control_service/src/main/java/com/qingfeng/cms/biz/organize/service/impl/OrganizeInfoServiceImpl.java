package com.qingfeng.cms.biz.organize.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.organize.dao.OrganizeInfoDao;
import com.qingfeng.cms.biz.organize.service.OrganizeInfoService;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoSaveDTO;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoUpdateDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 社团组织详情表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Service
public class OrganizeInfoServiceImpl extends ServiceImpl<OrganizeInfoDao, OrganizeInfoEntity> implements OrganizeInfoService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存社团组织详情信息
     * @param organizeInfoSaveDTO
     * @param userId
     */
    @Override
    public void saveOrganizeInfo(OrganizeInfoSaveDTO organizeInfoSaveDTO, Long userId) {
        OrganizeInfoEntity organizeInfoEntity = dozerUtils.map2(organizeInfoSaveDTO, OrganizeInfoEntity.class);
        organizeInfoEntity.setUserId(userId);

        baseMapper.insert(organizeInfoEntity);
    }

    /**
     * 修改社团保存信息
     * @param organizeInfoUpdateDTO
     * @param userId
     */
    @Override
    public void updateOrganizeInfoById(OrganizeInfoUpdateDTO organizeInfoUpdateDTO, Long userId) {
        OrganizeInfoEntity organizeInfoEntity = dozerUtils.map2(organizeInfoUpdateDTO, OrganizeInfoEntity.class);
        organizeInfoEntity.setUserId(userId);

        baseMapper.updateById(organizeInfoEntity);
    }

    /**
     * 删除视频信息
     * @param vodId
     */
    @Override
    public void removeVodId(String vodId) {
        OrganizeInfoEntity organizeInfoEntity = baseMapper.selectOne(Wraps.lbQ(new OrganizeInfoEntity())
                .eq(OrganizeInfoEntity::getVideo, vodId));
        organizeInfoEntity.setVideo("")
                .setVideoName("");
        baseMapper.updateById(organizeInfoEntity);
    }
}