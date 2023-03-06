package com.qingfeng.cms.biz.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.apply.dto.ActiveCheckQueryDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.cms.domain.apply.ro.ApplyCheckRo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 活动审核表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Repository
public interface ApplyCheckDao extends BaseMapper<ApplyCheckEntity> {

    /**
     * 查询总记录数
     * @param activeCheckQueryDTO
     * @return
     */
    Integer selectCountByQuery(@Param("activeCheckQueryDTO") ActiveCheckQueryDTO activeCheckQueryDTO);

    /**
     * 查询需要活动终审的活动列表
     * @param activeCheckQueryDTO
     * @return
     */
    List<ApplyCheckRo> selectApplyCheckList(@Param("activeCheckQueryDTO") ActiveCheckQueryDTO activeCheckQueryDTO);
}
