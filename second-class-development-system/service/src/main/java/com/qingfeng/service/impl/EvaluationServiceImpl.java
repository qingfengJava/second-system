package com.qingfeng.service.impl;

import com.qingfeng.constant.ActiveTypeConstant;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.EvaluationMapper;
import com.qingfeng.dao.ScoreMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.Evaluation;
import com.qingfeng.entity.Score;
import com.qingfeng.service.EvaluationService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.utils.SchoolYearUtils;
import com.qingfeng.vo.ResultVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public ResultVO selectEvaluationByUid(Integer uid, Integer applyId) {
        //根据用户id和活动id查询评价
        Evaluation evaluation = evaluationMapper.selectEvaluationByUid(uid, applyId);
        //直接将查询的结果返回
        return new ResultVO(ResStatus.OK, "success", evaluation);
    }

    /**
     * 添加活动评价
     *  注意：添加活动评价的同时，需要给对应的同学添加学分
     * @param uid
     * @param applyId
     * @param evaluation
     * @return
     */
    @Override
    @Transactional
    public ResultVO addEvaluation(Integer uid, Integer applyId, Evaluation evaluation) {
        //先添加活动评价
        evaluation.setUid(uid);
        evaluation.setParentId(0);
        evaluation.setApplyActiveId(applyId);
        evaluation.setStar(0);
        evaluation.setCreateTime(new Date());
        evaluation.setIsDeleted(0);
        int i = evaluationMapper.insertUseGeneratedKeys(evaluation);
        if (i > 0){
            //添加成功，则添加学分   先查询该活动的学分及类型
            Apply apply = applyMapper.selectByPrimaryKey(applyId);
            Double score = apply.getActiveScore();
            Integer type = apply.getActiveType();
            //查询得分表
            Example example = new Example(Score.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userid", uid);
            Score oldScore = scoreMapper.selectOneByExample(example);
            //判断是那个活动类型
            oldScore.setTotalScore(oldScore.getTotalScore()+score);
            if (type.equals(ActiveTypeConstant.THOUGHT_POLITICAL_AND_ETHICS)){
                oldScore.setIdeologyPoliticsScore(oldScore.getIdeologyPoliticsScore()+score);
            }else if (type.equals(ActiveTypeConstant.ACADEMIC_TECHNOLOGY_AND_INNOVATION)){
                oldScore.setScienceTechnologyScore(oldScore.getScienceTechnologyScore()+score);
            }else if (type.equals(ActiveTypeConstant.CULTURE_COMMUNICATION_AND_INTERACTION)){
                oldScore.setCultureArtScore(oldScore.getCultureArtScore()+score);
            }else if (type.equals(ActiveTypeConstant.CLUB_ACTIVITY_AND_WORK_HISTORY)){
                oldScore.setClubJobScore(oldScore.getClubJobScore()+score);
            }else if (type.equals(ActiveTypeConstant.SOCIAL_PRACTICE_AND_VOLUNTEER_SERVICE)){
                oldScore.setSocialVolunteerScore(oldScore.getSocialVolunteerScore()+score);
            }else if (type.equals(ActiveTypeConstant.SKILL_TRAINING_AND_OTHER)){
                oldScore.setSkillTrainScore(oldScore.getSkillTrainScore()+score);
            }
            int j = scoreMapper.updateByPrimaryKey(oldScore);
            if (j > 0){
                return new ResultVO(ResStatus.OK, "评价成功！",null);
            }
        }
        return new ResultVO(ResStatus.NO, "评价失败！",null);
    }

    /**
     * 添加活动的子评价
     *      子评价就没有满意程度的评价，只有评价内容
     * @param uid
     * @param applyId
     * @param parentId
     * @param evaluation
     * @return
     */
    @Override
    public ResultVO addChildEvaluation(Integer uid, Integer applyId, Integer parentId, Evaluation evaluation) {
        evaluation.setUid(uid);
        //parentId是父评价的主键id
        evaluation.setParentId(parentId);
        evaluation.setApplyActiveId(applyId);
        evaluation.setStar(0);
        evaluation.setCreateTime(new Date());
        evaluation.setIsDeleted(0);
        int i = evaluationMapper.insertUseGeneratedKeys(evaluation);
        if (i > 0){
            return new ResultVO(ResStatus.OK, "评价成功！",null);
        }
        return new ResultVO(ResStatus.NO, "评价失败！",null);
    }

    /**
     * 分页查询主活动评价
     * @param applyId
     * @param pageNum
     * @param limit
     * @return
     */
    @Override
    public ResultVO selectEvaluationByApplyId(Integer applyId, int pageNum, int limit) {
        //封装查询条件
        Example example = new Example(Evaluation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyActiveId", applyId);
        criteria.andEqualTo("parentId", 0);
        criteria.andEqualTo("isDeleted", 0);

        int start = (pageNum-1)*limit;
        RowBounds rowBounds = new RowBounds(start,limit);
        //查询分页数据
        List<Evaluation> evaluations = evaluationMapper.selectByExampleAndRowBounds(example, rowBounds);
        //查询总记录数
        int count = evaluationMapper.selectCountByExample(example);
        //计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        //封装数据
        PageHelper<Evaluation> pageHelper = new PageHelper<>(count, pageCount, evaluations);
        return new ResultVO(ResStatus.OK, "success", pageHelper);
    }

    @Override
    public ResultVO selectGradeByApplyId(Integer applyId) {
        //获取当前学年
        String str = SchoolYearUtils.getSchoolYearByOne();
        String schoolYear = str.substring(0, str.lastIndexOf("-"));
        //先查询该活动的评价星级集合
        Example example = new Example(Evaluation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyActiveId", applyId);
        criteria.andEqualTo("parentId", 0);
        criteria.andEqualTo("isDeleted", 0);
        criteria.andLike("schoolYear", "%"+schoolYear+"%");
        List<Evaluation> evaluations = evaluationMapper.selectByExample(example);
        double count = 0;
        double countScore = 0;
        if (evaluations.size() > 0){
            for (Evaluation evaluation : evaluations) {
                count += (evaluation.getFeelStar()+evaluation.getStar()+ evaluation.getServiceStar()+evaluation.getSatisfactionStar()) / 4.0;
            }
            countScore = count / evaluations.size();
        }
        return new ResultVO(ResStatus.OK, "success", countScore);
    }
}
