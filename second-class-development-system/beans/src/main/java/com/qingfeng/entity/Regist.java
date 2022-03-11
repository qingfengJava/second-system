package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 活动报名实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
     * 参与者类型   1：参与者   2：组织者
     */
    private Integer type;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date regCreateTime;

    /**
     * 是否报名成功（0：失败   1：成功） 没有报名成功的就是身份待审核的
     */
    @Column(name = "is_success")
    private Integer isSuccess;

    /**
     * 审核状态  0：身份待审核    1：直接报名就成功的   2：审核身份通过   3：审核身份不通过
     */
    private Integer status;

    /**
     * 审核的信息
     */
    @Column(name = "check_msg")
    private String checkMsg;

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


}