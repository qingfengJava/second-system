package com.qingfeng.cms.biz.organize.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.organize.dao.OrganizeImgDao;
import com.qingfeng.cms.biz.organize.service.OrganizeImgService;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社团组织图片信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class OrganizeImgServiceImpl extends ServiceImpl<OrganizeImgDao, OrganizeImgEntity> implements OrganizeImgService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * @param organizeId
     * @param userId
     * @return
     */
    @Override
    public List<OrganizeImgEntity> getImgList(Long organizeId, Long userId) {
        List<OrganizeImgEntity> organizeImgList = baseMapper.selectList(Wraps.lbQ(new OrganizeImgEntity())
                .eq(OrganizeImgEntity::getOrganizeId, organizeId));
        if (CollUtil.isNotEmpty(organizeImgList)) {
            return organizeImgList;
        } else {
            return baseMapper.selectList(Wraps.lbQ(new OrganizeImgEntity())
                    .eq(OrganizeImgEntity::getCreateUser, userId));
        }
    }

}