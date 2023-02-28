package com.qingfeng.cms.biz.sign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.sign.dto.ActiveQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveSignSaveDTO;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.cms.domain.sign.vo.ApplyPageVo;
import com.qingfeng.cms.domain.sign.vo.OrganizeVo;

import java.util.List;

/**
 * 活动报名表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
public interface ActiveSignService extends IService<ActiveSignEntity> {

    /**
     * 查询社团组织列表
     * @return
     */
    List<OrganizeVo> organizeList();

    /**
     * 查询已发布的活动
     * @param activeQueryDTO
     * @param userId
     * @return
     */
    ApplyPageVo applyList(ActiveQueryDTO activeQueryDTO, Long userId);

    /**
     * 活动报名
     * @param activeSignSaveDTO
     * @param userId
     */
    void saveSign(ActiveSignSaveDTO activeSignSaveDTO, Long userId);

}

