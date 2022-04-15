package com.qingfeng.service.impl;

import com.qingfeng.constant.ActiveLevelConstant;
import com.qingfeng.constant.ActiveTypeConstant;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.EvaluationMapper;
import com.qingfeng.dao.UserInfoMapper;
import com.qingfeng.dto.ActiveNum;
import com.qingfeng.dto.SchoolYearScore;
import com.qingfeng.dto.TypeActiveNum;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.Evaluation;
import com.qingfeng.entity.UserInfo;
import com.qingfeng.service.DataAnalysisService;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ApplyMapper applyMapper;

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

    /**
     * 查询学生各个年级修的学分
     * @param uid
     * @return
     */
    @Override
    public ResultVO queryScore(Integer uid) {
        //先查询学生的入校年级
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("uid",uid);
        UserInfo userInfo = userInfoMapper.selectOneByExample(example);
        //年级
        int grade = Integer.parseInt(userInfo.getGrade().substring(0, 4));
        //学制
        Integer educationalSystem = userInfo.getEducationalSystem();
        //根据年级和学制查询学分  存储到list集合中
        List<Double> list = new ArrayList<>();
        //评价之后才能加学分  先查询评价表
        Example example1 = new Example(Evaluation.class);
        Example.Criteria criteria = example1.createCriteria();
        criteria.andEqualTo("uid",uid);
        criteria.andEqualTo("isDeleted",0);
        List<Evaluation> evaluations = evaluationMapper.selectByExample(example1);
        for (int i = 1; i <= educationalSystem; i++) {
            Double score = 0.0;
            for (Evaluation evaluation : evaluations) {
                //得到活动申请表的主键id
                Integer applyId = evaluation.getApplyActiveId();
                //拿到主键id条件查询是否满足条件的信息
                Example example2 = new Example(Apply.class);
                Example.Criteria criteria1 = example2.createCriteria();
                criteria1.andEqualTo("applyId",applyId);
                criteria1.andEqualTo("isEnd",1);
                criteria1.andEqualTo("isDelete",0);
                String schoolYear = grade+(i-1) + "-" + (grade + i);
                criteria1.andLike("schoolYear","%"+schoolYear+"%");
                Apply apply = applyMapper.selectOneByExample(example2);
                if (apply != null){
                    //查询到了就把学分加上
                    score += apply.getActiveScore();
                }
            }
            //将学分添加到list集合中
            list.add(score);
        }
        return new ResultVO(ResStatus.OK,"success",new SchoolYearScore(list,educationalSystem));
    }
}
