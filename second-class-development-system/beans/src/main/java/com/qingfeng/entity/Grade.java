package com.qingfeng.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 社团评级实体类
 *
 * @author 清风学Java
 */
public class Grade {
    /**
     * 社团评级表Id
     */
    @Id
    @Column(name = "grade_id")
    private Integer gradeId;

    /**
     * 社团Id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 学年
     */
    @Column(name = "school_year")
    private String schoolYear;

    /**
     * 年度活动个数
     */
    @Column(name = "active_num")
    private Integer activeNum;

    /**
     * 精品活动个数
     */
    @Column(name = "best_active_num")
    private Integer bestActiveNum;

    /**
     * 活动综合评价级别（星级）
     */
    @Column(name = "total_evaluation_level")
    private Integer totalEvaluationLevel;

    /**
     * 评价级别（星级）
     */
    @Column(name = "grade_level")
    private Integer gradeLevel;

    /**
     * 评价时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 评价人姓名
     */
    private String appraiser;

    /**
     * 评价人职位
     */
    private String job;

    /**
     * 获取社团评级表Id
     *
     * @return grade_id - 社团评级表Id
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * 设置社团评级表Id
     *
     * @param gradeId 社团评级表Id
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * 获取社团Id
     *
     * @return user_id - 社团Id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置社团Id
     *
     * @param userId 社团Id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取学年
     *
     * @return school_year - 学年
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     * 设置学年
     *
     * @param schoolYear 学年
     */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * 获取年度活动个数
     *
     * @return active_num - 年度活动个数
     */
    public Integer getActiveNum() {
        return activeNum;
    }

    /**
     * 设置年度活动个数
     *
     * @param activeNum 年度活动个数
     */
    public void setActiveNum(Integer activeNum) {
        this.activeNum = activeNum;
    }

    /**
     * 获取精品活动个数
     *
     * @return best_active_num - 精品活动个数
     */
    public Integer getBestActiveNum() {
        return bestActiveNum;
    }

    /**
     * 设置精品活动个数
     *
     * @param bestActiveNum 精品活动个数
     */
    public void setBestActiveNum(Integer bestActiveNum) {
        this.bestActiveNum = bestActiveNum;
    }

    /**
     * 获取活动综合评价级别（星级）
     *
     * @return total_evaluation_level - 活动综合评价级别（星级）
     */
    public Integer getTotalEvaluationLevel() {
        return totalEvaluationLevel;
    }

    /**
     * 设置活动综合评价级别（星级）
     *
     * @param totalEvaluationLevel 活动综合评价级别（星级）
     */
    public void setTotalEvaluationLevel(Integer totalEvaluationLevel) {
        this.totalEvaluationLevel = totalEvaluationLevel;
    }

    /**
     * 获取评价级别（星级）
     *
     * @return grade_level - 评价级别（星级）
     */
    public Integer getGradeLevel() {
        return gradeLevel;
    }

    /**
     * 设置评价级别（星级）
     *
     * @param gradeLevel 评价级别（星级）
     */
    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * 获取评价时间
     *
     * @return create_time - 评价时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置评价时间
     *
     * @param createTime 评价时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取评价人姓名
     *
     * @return appraiser - 评价人姓名
     */
    public String getAppraiser() {
        return appraiser;
    }

    /**
     * 设置评价人姓名
     *
     * @param appraiser 评价人姓名
     */
    public void setAppraiser(String appraiser) {
        this.appraiser = appraiser;
    }

    /**
     * 获取评价人职位
     *
     * @return job - 评价人职位
     */
    public String getJob() {
        return job;
    }

    /**
     * 设置评价人职位
     *
     * @param job 评价人职位
     */
    public void setJob(String job) {
        this.job = job;
    }
}