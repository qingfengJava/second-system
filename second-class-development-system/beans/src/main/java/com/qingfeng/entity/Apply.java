package com.qingfeng.entity;

import java.util.Date;
import javax.persistence.*;

public class Apply {
    /**
     * 活动申请的主键Id
     */
    @Id
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 申请活动的用户Id，关联用户表
     */
    @Column(name = "apply_user_id")
    private Integer applyUserId;

    /**
     * 活动的名称
     */
    @Column(name = "active_name")
    private String activeName;

    /**
     * 活动规模的人数范围
     */
    @Column(name = "active_num")
    private String activeNum;

    /**
     * 活动的类型（1：思想道德  2：学术创新 3：文化交往 4：社团工作 5：社会志愿 6：技能其他）
     */
    @Column(name = "active_type")
    private String activeType;

    /**
     * 活动层级 （1：校级活动  2：院级活动  3：一般活动）
     */
    @Column(name = "active_level")
    private String activeLevel;

    /**
     * 主办单位
     */
    @Column(name = "host_name")
    private String hostName;

    /**
     * 所属单位
     */
    @Column(name = "belong_department")
    private String belongDepartment;

    /**
     * 总负责人
     */
    @Column(name = "active_principal")
    private String activePrincipal;

    /**
     * 总负责人联系电话
     */
    @Column(name = "active_principal_tel_num")
    private String activePrincipalTelNum;

    /**
     * 指导老师
     */
    @Column(name = "teacher_name")
    private String teacherName;

    /**
     * 指导老师联系电话
     */
    @Column(name = "teacher_telephone_num")
    private String teacherTelephoneNum;

    /**
     * 活动地点
     */
    @Column(name = "active_location")
    private String activeLocation;

    /**
     * 活动时间
     */
    @Column(name = "active_time")
    private String activeTime;

    /**
     * 活动分值
     */
    @Column(name = "active-score")
    private Double activeScore;

    /**
     * 活动简介
     */
    @Column(name = "active_introduction")
    private String activeIntroduction;

    /**
     * 活动内容
     */
    @Column(name = "active_content")
    private String activeContent;

    /**
     * 活动申请的时间
     */
    @Column(name = "apply_create_time")
    private Date applyCreateTime;

    /**
     * 是否删除（0：未删除  1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 获取活动申请的主键Id
     *
     * @return apply_id - 活动申请的主键Id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置活动申请的主键Id
     *
     * @param applyId 活动申请的主键Id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取申请活动的用户Id，关联用户表
     *
     * @return apply_user_id - 申请活动的用户Id，关联用户表
     */
    public Integer getApplyUserId() {
        return applyUserId;
    }

    /**
     * 设置申请活动的用户Id，关联用户表
     *
     * @param applyUserId 申请活动的用户Id，关联用户表
     */
    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    /**
     * 获取活动的名称
     *
     * @return active_name - 活动的名称
     */
    public String getActiveName() {
        return activeName;
    }

    /**
     * 设置活动的名称
     *
     * @param activeName 活动的名称
     */
    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    /**
     * 获取活动规模的人数范围
     *
     * @return active_num - 活动规模的人数范围
     */
    public String getActiveNum() {
        return activeNum;
    }

    /**
     * 设置活动规模的人数范围
     *
     * @param activeNum 活动规模的人数范围
     */
    public void setActiveNum(String activeNum) {
        this.activeNum = activeNum;
    }

    /**
     * 获取活动的类型（1：思想道德  2：学术创新 3：文化交往 4：社团工作 5：社会志愿 6：技能其他）
     *
     * @return active_type - 活动的类型（1：思想道德  2：学术创新 3：文化交往 4：社团工作 5：社会志愿 6：技能其他）
     */
    public String getActiveType() {
        return activeType;
    }

    /**
     * 设置活动的类型（1：思想道德  2：学术创新 3：文化交往 4：社团工作 5：社会志愿 6：技能其他）
     *
     * @param activeType 活动的类型（1：思想道德  2：学术创新 3：文化交往 4：社团工作 5：社会志愿 6：技能其他）
     */
    public void setActiveType(String activeType) {
        this.activeType = activeType;
    }

    /**
     * 获取活动层级 （1：校级活动  2：院级活动  3：一般活动）
     *
     * @return active_level - 活动层级 （1：校级活动  2：院级活动  3：一般活动）
     */
    public String getActiveLevel() {
        return activeLevel;
    }

    /**
     * 设置活动层级 （1：校级活动  2：院级活动  3：一般活动）
     *
     * @param activeLevel 活动层级 （1：校级活动  2：院级活动  3：一般活动）
     */
    public void setActiveLevel(String activeLevel) {
        this.activeLevel = activeLevel;
    }

    /**
     * 获取主办单位
     *
     * @return host_name - 主办单位
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 设置主办单位
     *
     * @param hostName 主办单位
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 获取所属单位
     *
     * @return belong_department - 所属单位
     */
    public String getBelongDepartment() {
        return belongDepartment;
    }

    /**
     * 设置所属单位
     *
     * @param belongDepartment 所属单位
     */
    public void setBelongDepartment(String belongDepartment) {
        this.belongDepartment = belongDepartment;
    }

    /**
     * 获取总负责人
     *
     * @return active_principal - 总负责人
     */
    public String getActivePrincipal() {
        return activePrincipal;
    }

    /**
     * 设置总负责人
     *
     * @param activePrincipal 总负责人
     */
    public void setActivePrincipal(String activePrincipal) {
        this.activePrincipal = activePrincipal;
    }

    /**
     * 获取总负责人联系电话
     *
     * @return active_principal_tel_num - 总负责人联系电话
     */
    public String getActivePrincipalTelNum() {
        return activePrincipalTelNum;
    }

    /**
     * 设置总负责人联系电话
     *
     * @param activePrincipalTelNum 总负责人联系电话
     */
    public void setActivePrincipalTelNum(String activePrincipalTelNum) {
        this.activePrincipalTelNum = activePrincipalTelNum;
    }

    /**
     * 获取指导老师
     *
     * @return teacher_name - 指导老师
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 设置指导老师
     *
     * @param teacherName 指导老师
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * 获取指导老师联系电话
     *
     * @return teacher_telephone_num - 指导老师联系电话
     */
    public String getTeacherTelephoneNum() {
        return teacherTelephoneNum;
    }

    /**
     * 设置指导老师联系电话
     *
     * @param teacherTelephoneNum 指导老师联系电话
     */
    public void setTeacherTelephoneNum(String teacherTelephoneNum) {
        this.teacherTelephoneNum = teacherTelephoneNum;
    }

    /**
     * 获取活动地点
     *
     * @return active_location - 活动地点
     */
    public String getActiveLocation() {
        return activeLocation;
    }

    /**
     * 设置活动地点
     *
     * @param activeLocation 活动地点
     */
    public void setActiveLocation(String activeLocation) {
        this.activeLocation = activeLocation;
    }

    /**
     * 获取活动时间
     *
     * @return active_time - 活动时间
     */
    public String getActiveTime() {
        return activeTime;
    }

    /**
     * 设置活动时间
     *
     * @param activeTime 活动时间
     */
    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    /**
     * 获取活动分值
     *
     * @return active-score - 活动分值
     */
    public Double getActiveScore() {
        return activeScore;
    }

    /**
     * 设置活动分值
     *
     * @param activeScore 活动分值
     */
    public void setActiveScore(Double activeScore) {
        this.activeScore = activeScore;
    }

    /**
     * 获取活动简介
     *
     * @return active_introduction - 活动简介
     */
    public String getActiveIntroduction() {
        return activeIntroduction;
    }

    /**
     * 设置活动简介
     *
     * @param activeIntroduction 活动简介
     */
    public void setActiveIntroduction(String activeIntroduction) {
        this.activeIntroduction = activeIntroduction;
    }

    /**
     * 获取活动内容
     *
     * @return active_content - 活动内容
     */
    public String getActiveContent() {
        return activeContent;
    }

    /**
     * 设置活动内容
     *
     * @param activeContent 活动内容
     */
    public void setActiveContent(String activeContent) {
        this.activeContent = activeContent;
    }

    /**
     * 获取活动申请的时间
     *
     * @return apply_create_time - 活动申请的时间
     */
    public Date getApplyCreateTime() {
        return applyCreateTime;
    }

    /**
     * 设置活动申请的时间
     *
     * @param applyCreateTime 活动申请的时间
     */
    public void setApplyCreateTime(Date applyCreateTime) {
        this.applyCreateTime = applyCreateTime;
    }

    /**
     * 获取是否删除（0：未删除  1：已删除）
     *
     * @return is_delete - 是否删除（0：未删除  1：已删除）
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（0：未删除  1：已删除）
     *
     * @param isDelete 是否删除（0：未删除  1：已删除）
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}