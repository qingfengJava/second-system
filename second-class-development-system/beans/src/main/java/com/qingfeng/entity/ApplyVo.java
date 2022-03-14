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
 * 活动申请实体类，维护用户实体对象
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplyVo {
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
    private Integer activeType;

    /**
     * 活动层级 （1：校级活动  2：院级活动  3：一般活动）
     */
    @Column(name = "active_level")
    private Integer activeLevel;

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
     * 活动报名截止时间
     */
    @Column(name = "registration_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registrationDeadline;

    /**
     * 活动报名是否限额
     */
    @Column(name = "is_quota")
    private Integer isQuota;

    /**
     * 学期
     */
    @Column(name = "school_year")
    private String schoolYear;

    /**
     * 活动分值
     */
    @Column(name = "active_score")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyCreateTime;

    /**
     * 活动申请是否同意，0：未同意  1：已同意
     */
    @Column(name = "is_agree")
    private Integer isAgree;

    /**
     * 活动报名是否结束  0：未结束  1：已结束
     */
    @Column(name = "is_end")
    private Integer isEnd;

    /**
     * 是否删除（0：未删除  1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 活动申请维护申请用户信息
     */
    private UsersVo usersVo;
}