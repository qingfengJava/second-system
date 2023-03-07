package com.qingfeng.cms.biz.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.evaluation.dao.EvaluationFeedbackDao;
import com.qingfeng.cms.biz.evaluation.service.EvaluationFeedbackService;
import com.qingfeng.cms.domain.evaluation.entity.EvaluationFeedbackEntity;
import org.springframework.stereotype.Service;


/**
 * 项目评价在线反馈表(对已参与的项目进行评价反馈)（类似于间接评价）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Service
public class EvaluationFeedbackServiceImpl extends ServiceImpl<EvaluationFeedbackDao, EvaluationFeedbackEntity> implements EvaluationFeedbackService {

}