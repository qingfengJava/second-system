package com.qingfeng.dao;

import com.qingfeng.entity.Apply;
import com.qingfeng.generaldao.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 活动申请持久层
 *
 * @author 清风学Java
 */
@Repository
public interface ApplyMapper extends GeneralDao<Apply> {

    /**
     * 根据活动报名表主键Id，查询对应的活动信息
     * @param applyId
     * @return
     */
    Apply queryApplyById(Integer applyId);

    /**
     * 分页查询新活动
     * @param start
     * @param limit
     * @return
     */
    List<Apply> queryApply(@Param("start") int start,
                           @Param("limit") int limit);
}