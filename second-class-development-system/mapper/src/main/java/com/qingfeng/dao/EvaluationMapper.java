package com.qingfeng.dao;

import com.qingfeng.entity.Evaluation;
import com.qingfeng.generaldao.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 活动评价的持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface EvaluationMapper extends GeneralDao<Evaluation> {

    /**
     * 根据用户Id和活动id查询评价
     * @param uid
     * @param applyId
     * @return
     */
    Evaluation selectEvaluationByUid(@Param("uid") Integer uid,
                                     @Param("applyId") Integer applyId);
}