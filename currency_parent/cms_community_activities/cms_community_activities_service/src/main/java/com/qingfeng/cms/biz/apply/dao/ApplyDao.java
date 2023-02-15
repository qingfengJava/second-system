package com.qingfeng.cms.biz.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.apply.dto.ApplyQueryDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 社团活动申请表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Repository
public interface ApplyDao extends BaseMapper<ApplyEntity> {

    /**
     * 分页条件查询活动申请列表
     * @param applyQueryDTO
     * @param userId
     * @return
     */
    List<ApplyEntity> findList(@Param("applyQueryDTO") ApplyQueryDTO applyQueryDTO,
                               @Param("userId") Long userId);
}
