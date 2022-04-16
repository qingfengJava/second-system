package com.qingfeng.service;

import com.qingfeng.entity.Evaluation;
import com.qingfeng.vo.ResultVO;

/**
 * 活动评价的业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/11
 */
public interface EvaluationService {

    /**
     * 根据用户Id和活动Id查询评价信息
     *
     * @param uid
     * @param applyId
     * @return
     */
    ResultVO selectEvaluationByUid(Integer uid, Integer applyId);

    /**
     * 添加活动评价
     * @param uid
     * @param applyId
     * @param evaluation
     * @return
     */
    ResultVO addEvaluation(Integer uid, Integer applyId, Evaluation evaluation);

    /**
     * 添加活动的子评价
     * @param uid
     * @param applyId
     * @param parentId
     * @param evaluation
     * @return
     */
    ResultVO addChildEvaluation(Integer uid, Integer applyId, Integer parentId, Evaluation evaluation);

    /**
     * 分页查询主评论信息
     * @param applyId
     * @param pageNum
     * @param limit
     * @return
     */
    ResultVO selectEvaluationByApplyId(Integer applyId, int pageNum, int limit);
}
