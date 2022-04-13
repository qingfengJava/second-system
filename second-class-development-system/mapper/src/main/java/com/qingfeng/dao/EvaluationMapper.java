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

    /**
     * 查询一般活动参与成功的活动个数
     * @param uid
     * @param level
     * @return
     */
    Integer queryActiveLevelNum(@Param("uid") Integer uid,
                                @Param("level") Integer level);

    /**
     * 查询思想道德类型活动的数量
     * @param uid
     * @param type
     * @return
     */
    Integer queryActiveTypeNum(@Param("uid") Integer uid,
                               @Param("type") Integer type);

}