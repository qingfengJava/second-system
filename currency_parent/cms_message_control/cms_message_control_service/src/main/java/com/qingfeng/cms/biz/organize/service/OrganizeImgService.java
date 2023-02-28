package com.qingfeng.cms.biz.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;

import java.util.List;
import java.util.Map;

/**
 * 社团组织图片信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
public interface OrganizeImgService extends IService<OrganizeImgEntity> {

    /**
     * 查询社团设置的图片信息
     * @param organizeId
     * @param userId
     * @return
     */
    List<OrganizeImgEntity> getImgList(Long organizeId, Long userId);

    /**
     * 根据社团Ids查询社团的轮播图信息
     * @param organizeIds
     * @return
     */
    Map<Long, List<OrganizeImgEntity>> getImgLists(List<Long> organizeIds);
}

