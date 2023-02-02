package com.qingfeng.cms.biz.organize.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.organize.dao.OrganizeInfoDao;
import com.qingfeng.cms.biz.organize.service.OrganizeInfoService;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoSaveDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
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

    /**
     * 保存社团组织详情信息
     * @param organizeInfoSaveDTO
     */
    @Override
    public void saveOrganizeInfo(OrganizeInfoSaveDTO organizeInfoSaveDTO) {

    }
}