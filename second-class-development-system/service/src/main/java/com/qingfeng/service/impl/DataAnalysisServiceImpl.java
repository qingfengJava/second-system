package com.qingfeng.service.impl;

import com.qingfeng.constant.ActiveLevelConstant;
import com.qingfeng.constant.ActiveTypeConstant;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.*;
import com.qingfeng.dto.ActiveNum;
import com.qingfeng.dto.ActiveQuality;
import com.qingfeng.dto.SchoolYearScore;
import com.qingfeng.dto.TypeActiveNum;
import com.qingfeng.entity.*;
import com.qingfeng.service.DataAnalysisService;
import com.qingfeng.utils.SchoolYearUtils;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private OrganizeMapper organizeMapper;
    @Autowired
    private GradeMapper gradeMapper;

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

    /**
     * 年度活动数目
     * @param year
     * @return
     */
    @Override
    public ResultVO queryActiveTypeNum(Integer year) {
        //首先要根据年得到一个学年
        String schoolYear= SchoolYearUtils.getSchoolYearByOne(year);
        //查询各种类型活动在该学年的数量
        TypeActiveNum typeActiveNum = new TypeActiveNum();
        //思想道德
        for (int i = 1; i <= 6; i++) {
            Example example = new Example(Apply.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isAgree",1);
            criteria.andEqualTo("isCheck",1);
            criteria.andEqualTo("isEnd",1);
            criteria.andEqualTo("isDelete",0);
            criteria.andLike("schoolYear",schoolYear+"%");
            criteria.andEqualTo("activeType",i);
            Integer activeNum = applyMapper.selectCountByExample(example);
            switch (i){
                case 1:
                    typeActiveNum.setMoralNum(activeNum);
                    break;
                case 2:
                    typeActiveNum.setAcademicNum(activeNum);
                    break;
                case 3:
                    typeActiveNum.setCultureNum(activeNum);
                    break;
                case 4:
                    typeActiveNum.setClubWork(activeNum);
                    break;
                case 5:
                    typeActiveNum.setVolunteerNum(activeNum);
                    break;
                case 6:
                    typeActiveNum.setOtherSkill(activeNum);
                    break;
                default:
                    break;
            }
        }
        return new ResultVO(ResStatus.OK,schoolYear,typeActiveNum);
    }

    /**
     * 第二课堂年度活动质量分析情况（统计的是不用评价质量活动的个数）
     *      1、首先要查出该年度所有的活动
     *      2、根据活动查询活动的评价质量
     *      3、创建一个map集合，key为活动质量类型，value为该评价类型的活动数量
     * @param year
     * @return
     */
    @Override
    public ResultVO queryActiveQuality(Integer year) {
        //首先要根据年得到一个学年
        String schoolYear= SchoolYearUtils.getSchoolYearByOne(year);
        //查询所有活动
        Example applyExample = new Example(Apply.class);
        Example.Criteria criteria = applyExample.createCriteria();
        criteria.andEqualTo("isAgree",1);
        criteria.andEqualTo("isCheck",1);
        criteria.andEqualTo("isEnd",1);
        criteria.andEqualTo("isDelete",0);
        criteria.andLike("schoolYear",schoolYear+"%");
        //创建一个map集合，key为活动质量类型，value为该评价类型的活动数量   并设置初始值
        Map<String,Integer> map = new HashMap<>(10);
        map.put("优",0);
        map.put("良",0);
        map.put("差",0);
        List<Apply> applyList = applyMapper.selectByExample(applyExample);
        for (Apply apply : applyList) {
            //根据活动查询活动的评价质量
            Example evaluationExample = new Example(Evaluation.class);
            Example.Criteria evaluationCriteria = evaluationExample.createCriteria();
            evaluationCriteria.andEqualTo("applyActiveId",apply.getApplyId());
            evaluationCriteria.andEqualTo("isDeleted",0);
            List<Evaluation> evaluations = evaluationMapper.selectByExample(evaluationExample);
            int totalScore = 0;
            int count = 0;
            for (Evaluation evaluation : evaluations) {
                totalScore += evaluation.getFeelStar()+evaluation.getSatisfactionStar()+evaluation.getServiceStar()+evaluation.getGainStar();
                count++;
            }
            double score = count == 0 ? 0 : totalScore / count * 5.0;
            if (score >= 0.9){
                map.put("优",map.get("优")+1);
            }else if (score >= 0.6){
                map.put("良",map.get("良")+1);
            }else{
                map.put("差",map.get("差")+1);
            }
        }
        ActiveQuality activeQuality = new ActiveQuality(map.get("优"), map.get("良"), map.get("差"));
        return new ResultVO(ResStatus.OK,schoolYear,activeQuality);
    }

    /**
     * 查询社团年度活动举办的情况
     * @param uid
     * @param year
     * @return
     */
    @Override
    public ResultVO queryActiveType(Integer uid,Integer year) {
        //首先要根据年得到一个学年
        String schoolYear= SchoolYearUtils.getSchoolYearByOne(year);
        //查询各种类型活动在该学年的数量
        TypeActiveNum typeActiveNum = new TypeActiveNum();
        //思想道德
        for (int i = 1; i <= 6; i++) {
            Example example = new Example(Apply.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isAgree",1);
            criteria.andEqualTo("isCheck",1);
            criteria.andEqualTo("isEnd",1);
            criteria.andEqualTo("isDelete",0);
            criteria.andEqualTo("applyUserId",uid);
            criteria.andLike("schoolYear",schoolYear+"%");
            criteria.andEqualTo("activeType",i);
            Integer activeNum = applyMapper.selectCountByExample(example);
            switch (i){
                case 1:
                    typeActiveNum.setMoralNum(activeNum);
                    break;
                case 2:
                    typeActiveNum.setAcademicNum(activeNum);
                    break;
                case 3:
                    typeActiveNum.setCultureNum(activeNum);
                    break;
                case 4:
                    typeActiveNum.setClubWork(activeNum);
                    break;
                case 5:
                    typeActiveNum.setVolunteerNum(activeNum);
                    break;
                case 6:
                    typeActiveNum.setOtherSkill(activeNum);
                    break;
                default:
                    break;
            }
        }
        return new ResultVO(ResStatus.OK,schoolYear,typeActiveNum);
    }

    /**
     * 查询社团历年评级情况（6年为一组）
     * @param uid
     * @param year
     * @return
     */
    @Override
    public ResultVO queryActiveGrade(Integer uid, Integer year) {
        //根据用户id查询社团
        Example example = new Example(Organize.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete",0);
        criteria.andEqualTo("userId",uid);
        Organize organize = organizeMapper.selectOneByExample(example);
        //声明一个集合，用来存放每一年的评级情况
        ArrayList<Integer> activeGradeList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            //首先要根据年得到一个学年
            String schoolYear= SchoolYearUtils.getSchoolYearByOne(year-i);
            //查询每个学年社团的评级情况
            Example gradeExample = new Example(Grade.class);
            Example.Criteria gradeCriteria = gradeExample.createCriteria();
            gradeCriteria.andEqualTo("organizeId",organize.getOrganizeId());
            gradeCriteria.andLike("schoolYear","%"+schoolYear+"%");
            Grade grade = gradeMapper.selectOneByExample(gradeExample);
            if (grade != null) {
                activeGradeList.add(grade.getGradeLevel());
            }else{
                activeGradeList.add(0);
            }
        }

        return new ResultVO(ResStatus.OK,"success",activeGradeList);
    }
}
