package com.qingfeng.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelIgnore
    private Integer teacherInfoId;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty(value = "出生日期", index = 0)
    private Date birth;

    /**
     * 用户外键Id
     */
    @ExcelProperty(value = "用户表外键", index = 1)
    private Integer uid;

    /**
     * 名族
     */
    @ExcelProperty(value = "名族", index = 2)
    private String nation;

    /**
     * 性别（1：男   2：女）
     */
    @ExcelProperty(value = "性别", index = 3)
    private Integer sex;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status")
    @ExcelProperty(value = "政治面貌", index = 4)
    private String politicsStatus;

    /**
     * 所在学院
     */
    @ExcelProperty(value = "所属学院", index = 5)
    private String department;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历", index = 6)
    private String education;

    /**
     * 职称（1：讲师  2：副教授  3：教授）
     */
    @ExcelProperty(value = "职称", index = 7)
    @Column(name = "professional_title")
    private Integer professionalTitle;

    /**
     * 职务
     */
    @ExcelProperty(value = "职务", index = 8)
    private String duty;

    /**
     * 研究方向
     */
    @ExcelProperty(value = "研究方向", index = 9)
    @Column(name = "research_orientation")
    private String researchOrientation;

    /**
     * 通讯地址
     */
    @ExcelProperty(value = "通讯地址", index = 10)
    @Column(name = "postal_address")
    private String postalAddress;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 11)
    private String email;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话", index = 12)
    private String phone;

    /**
     * 手机
     */
    @ExcelProperty(value = "手机", index = 13)
    @Column(name = "cell_phone")
    private String cellPhone;

    /**
     * 籍贯
     */
    @ExcelProperty(value = "籍贯", index = 14)
    @Column(name = "native_place")
    private String nativePlace;

    /**
     * 个人简介
     */
    @ExcelProperty(value = "个人简介", index = 15)
    @Column(name = "individual_resume")
    private String individualResume;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 16)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 17)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除（0：未删除    1：已删除）
     */
    @ExcelProperty(value = "是否删除", index = 18)
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 是否可以修改
     */
    @ExcelProperty(value = "是否可以修改", index = 19)
    @Column(name = "is_change")
    private Integer isChange;
}