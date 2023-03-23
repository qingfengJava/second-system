package com.qingfeng.cms.biz.total.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
import com.qingfeng.cms.domain.total.vo.StuModuleDataAnalysisVo;
import com.qingfeng.cms.domain.total.vo.StudentScoreDetailsVo;
import com.qingfeng.cms.domain.total.vo.StudentScoreTotalVo;

import java.util.List;

/**
 * 用户总得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
public interface StudentScoreTotalService extends IService<StudentScoreTotalEntity> {

    /**
     * 查询学生模块得分情况
     * @param userId
     * @return
     */
    StudentScoreTotalVo stuModuleScore(Long userId);

    /**
     * 查询得分详情
     * @param userId
     * @return
     */
    List<StudentScoreDetailsVo> queryScoreDetails(Long userId);

    /**
     * 统计学生各个模块的活动参与情况
     * @param userId
     * @return
     */
    StuModuleDataAnalysisVo moduleDataAnalysis(Long userId);
}

