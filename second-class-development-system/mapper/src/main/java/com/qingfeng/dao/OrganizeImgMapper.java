package com.qingfeng.dao;

import com.qingfeng.entity.OrganizeImg;
import com.qingfeng.generaldao.GeneralDao;

import java.util.List;

/**
 * 社团组织轮播图信息持久层
 *
 * @author 清风学Java
 */
public interface OrganizeImgMapper extends GeneralDao<OrganizeImg> {

    /**
     * 根据社团组织Id查询社团对应的轮播图信息
     * @param organizeId
     * @return
     */
    List<OrganizeImg> selectOrganizeImgByOrganizationId(Integer organizeId);

}