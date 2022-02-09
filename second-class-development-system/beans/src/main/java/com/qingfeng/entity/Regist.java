package com.qingfeng.entity;

import java.util.Date;
import javax.persistence.*;

public class Regist {
    /**
     * 报名表主键Id
     */
    @Id
    @Column(name = "active_reg_id")
    private Integer activeRegId;

    /**
     * 外键 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 外键  活动表id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 学生学号
     */
    @Column(name = "student_id")
    private String studentId;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 所属学院
     */
    @Column(name = "student_college")
    private String studentCollege;

    /**
     * 学生所属专业
     */
    @Column(name = "student_major")
    private String studentMajor;

    /**
     * 参与者类型
     */
    private String type;

    /**
     * 学生性别（1：男  2：女）
     */
    @Column(name = "student_sex")
    private Integer studentSex;

    /**
     * 电话号码
     */
    @Column(name = "student_tel")
    private String studentTel;

    /**
     * QQ号码
     */
    @Column(name = "student_qq")
    private String studentQq;

    /**
     * 报名活动的时间
     */
    @Column(name = "reg_create_time")
    private Date regCreateTime;

    /**
     * 是否报名成功（0：失败   1：成功）
     */
    @Column(name = "is_success")
    private Integer isSuccess;

    /**
     * 是否删除（0：未删除  1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 是否签到（0：已签到  1：未签到）
     */
    @Column(name = "is_sign")
    private Integer isSign;

    /**
     * 获取报名表主键Id
     *
     * @return active_reg_id - 报名表主键Id
     */
    public Integer getActiveRegId() {
        return activeRegId;
    }

    /**
     * 设置报名表主键Id
     *
     * @param activeRegId 报名表主键Id
     */
    public void setActiveRegId(Integer activeRegId) {
        this.activeRegId = activeRegId;
    }

    /**
     * 获取外键 用户id
     *
     * @return user_id - 外键 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置外键 用户id
     *
     * @param userId 外键 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取外键  活动表id
     *
     * @return apply_id - 外键  活动表id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置外键  活动表id
     *
     * @param applyId 外键  活动表id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取学生学号
     *
     * @return student_id - 学生学号
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * 设置学生学号
     *
     * @param studentId 学生学号
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取学生姓名
     *
     * @return student_name - 学生姓名
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置学生姓名
     *
     * @param studentName 学生姓名
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 获取所属学院
     *
     * @return student_college - 所属学院
     */
    public String getStudentCollege() {
        return studentCollege;
    }

    /**
     * 设置所属学院
     *
     * @param studentCollege 所属学院
     */
    public void setStudentCollege(String studentCollege) {
        this.studentCollege = studentCollege;
    }

    /**
     * 获取学生所属专业
     *
     * @return student_major - 学生所属专业
     */
    public String getStudentMajor() {
        return studentMajor;
    }

    /**
     * 设置学生所属专业
     *
     * @param studentMajor 学生所属专业
     */
    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    /**
     * 获取参与者类型
     *
     * @return type - 参与者类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置参与者类型
     *
     * @param type 参与者类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取学生性别（1：男  2：女）
     *
     * @return student_sex - 学生性别（1：男  2：女）
     */
    public Integer getStudentSex() {
        return studentSex;
    }

    /**
     * 设置学生性别（1：男  2：女）
     *
     * @param studentSex 学生性别（1：男  2：女）
     */
    public void setStudentSex(Integer studentSex) {
        this.studentSex = studentSex;
    }

    /**
     * 获取电话号码
     *
     * @return student_tel - 电话号码
     */
    public String getStudentTel() {
        return studentTel;
    }

    /**
     * 设置电话号码
     *
     * @param studentTel 电话号码
     */
    public void setStudentTel(String studentTel) {
        this.studentTel = studentTel;
    }

    /**
     * 获取QQ号码
     *
     * @return student_qq - QQ号码
     */
    public String getStudentQq() {
        return studentQq;
    }

    /**
     * 设置QQ号码
     *
     * @param studentQq QQ号码
     */
    public void setStudentQq(String studentQq) {
        this.studentQq = studentQq;
    }

    /**
     * 获取报名活动的时间
     *
     * @return reg_create_time - 报名活动的时间
     */
    public Date getRegCreateTime() {
        return regCreateTime;
    }

    /**
     * 设置报名活动的时间
     *
     * @param regCreateTime 报名活动的时间
     */
    public void setRegCreateTime(Date regCreateTime) {
        this.regCreateTime = regCreateTime;
    }

    /**
     * 获取是否报名成功（0：失败   1：成功）
     *
     * @return is_success - 是否报名成功（0：失败   1：成功）
     */
    public Integer getIsSuccess() {
        return isSuccess;
    }

    /**
     * 设置是否报名成功（0：失败   1：成功）
     *
     * @param isSuccess 是否报名成功（0：失败   1：成功）
     */
    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
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

    /**
     * 获取是否签到（0：已签到  1：未签到）
     *
     * @return is_sign - 是否签到（0：已签到  1：未签到）
     */
    public Integer getIsSign() {
        return isSign;
    }

    /**
     * 设置是否签到（0：已签到  1：未签到）
     *
     * @param isSign 是否签到（0：已签到  1：未签到）
     */
    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }
}