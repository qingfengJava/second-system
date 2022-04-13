package com.qingfeng.service.impl;

import com.qingfeng.dao.EvaluationMapper;
import com.qingfeng.entity.Evaluation;
import com.qingfeng.service.EvaluationService;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动评价管理接口层实现
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/11
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Override
    public ResultVO selectEvaluationByUid(Integer uid, Integer applyId) {
        //根据用户id和活动id查询评价
        Evaluation evaluation = evaluationMapper.selectEvaluationByUid(uid, applyId);
        //直接将查询的结果返回
        return new ResultVO(ResStatus.OK, "success", evaluation);
    }
}
