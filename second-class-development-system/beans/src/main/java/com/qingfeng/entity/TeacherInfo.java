package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 学校领导信息详情表
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "teacher_info")
public class TeacherInfo {
    /**
     * 学校领导详情信息表主键Id
     */
    @Id
    @Column(name = "teacher_info_id")
    private Integer teacherInfoId;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birth;

    /**
     * 用户外键Id
     */
    private Integer uid;

    /**
     * 名族
     */
    private String nation;

    /**
     * 性别（1：男   2：女）
     */
    private Integer sex;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status")
    private String politicsStatus;

    /**
     * 所在学院
     */
    private String department;

    /**
     * 学历
     */
    private String education;

    /**
     * 职称（1：讲师  2：副教授  3：教授）
     */
    @Column(name = "professional_title")
    private Integer professionalTitle;

    /**
     * 职务
     */
    private String duty;

    /**
     * 研究方向
     */
    @Column(name = "research_orientation")
    private String researchOrientation;

    /**
     * 通讯地址
     */
    @Column(name = "postal_address")
    private String postalAddress;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    @Column(name = "cell_phone")
    private String cellPhone;

    /**
     * 个人简介
     */
    @Column(name = "individual_resume")
    private String individualResume;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除（0：未删除    1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;
}