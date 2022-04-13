package com.qingfeng.service.impl;

import com.qingfeng.constant.ActiveLevelConstant;
import com.qingfeng.constant.ActiveTypeConstant;
import com.qingfeng.dao.EvaluationMapper;
import com.qingfeng.dto.ActiveNum;
import com.qingfeng.dto.TypeActiveNum;
import com.qingfeng.service.DataAnalysisService;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据分析业务层接口实现
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/12
 */
@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    /**
     * 查询的是成功参与活动的人数
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public ResultVO queryActiveNum(Integer uid) {
        //查询一般活动数量
        Integer commonActive = evaluationMapper.queryActiveLevelNum(uid, ActiveLevelConstant.COMMON_LEVEL);
        //查询院级活动的数量
        Integer hospitalLevelActive = evaluationMapper.queryActiveLevelNum(uid,ActiveLevelConstant.INSTITUTE_LEVEL);
        //查询校级活动成功参与的数量
        Integer schoolLevelActive = evaluationMapper.queryActiveLevelNum(uid,ActiveLevelConstant.SCHOOL_LEVEL);
        ActiveNum activeNum = new ActiveNum(commonActive, hospitalLevelActive, schoolLevelActive);
        return new ResultVO(ResStatus.OK,"success",activeNum);
    }

    /**
     * 根据用户Id查询学生已经参与的各类型活动的数量
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public ResultVO queryTypeActiveNum(Integer uid) {
        //思想道德活动数量
        Integer moralNum = evaluationMapper.queryActiveTypeNum(uid, ActiveTypeConstant.THOUGHT_POLITICAL_AND_ETHICS);
        //学术创新活动数量
        Integer academicNum = evaluationMapper.queryActiveTypeNum(uid,ActiveTypeConstant.ACADEMIC_TECHNOLOGY_AND_INNOVATION);
        //文化交往活动数量
        Integer cultureNum = evaluationMapper.queryActiveTypeNum(uid,ActiveTypeConstant.CULTURE_COMMUNICATION_AND_INTERACTION);
        //社团工作活动数量
        Integer clubNum = evaluationMapper.queryActiveTypeNum(uid,ActiveTypeConstant.CLUB_ACTIVITY_AND_WORK_HISTORY);
        //社会志愿服务活动数量
        Integer volunteerNum = evaluationMapper.queryActiveTypeNum(uid,ActiveTypeConstant.SOCIAL_PRACTICE_AND_VOLUNTEER_SERVICE);
        //其他技能活动数量
        Integer otherNum = evaluationMapper.queryActiveTypeNum(uid,ActiveTypeConstant.SKILL_TRAINING_AND_OTHER);
        return new ResultVO(ResStatus.OK,"success",new TypeActiveNum(moralNum,academicNum,cultureNum,clubNum,volunteerNum,otherNum));
    }
}
