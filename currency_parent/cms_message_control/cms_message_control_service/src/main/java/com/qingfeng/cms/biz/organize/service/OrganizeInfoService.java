package com.qingfeng.cms.biz.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoSaveDTO;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoUpdateDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;

/**
 * 社团组织详情表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
public interface OrganizeInfoService extends IService<OrganizeInfoEntity> {

    /**
     * 保存社团组织详情信息
     * @param organizeInfoSaveDTO
     * @param userId
     */
    void saveOrganizeInfo(OrganizeInfoSaveDTO organizeInfoSaveDTO, Long userId);

    /**
     * 修改社团保存信息
     * @param organizeInfoUpdateDTO
     * @param userId
     */
    void updateOrganizeInfoById(OrganizeInfoUpdateDTO organizeInfoUpdateDTO, Long userId);

    /**
     * 删除vodId
     * @param vodId
     */
    void removeVodId(String vodId);
}

