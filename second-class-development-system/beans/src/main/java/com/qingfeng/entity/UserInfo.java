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
 * 用户详情信息实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user_info")
public class UserInfo {
    /**
     * 主键，无实意
     */
    @Id
    @Column(name = "user_info_id")
    private Integer userInfoId;

    /**
     * 学生表的外键，关联学生表信息
     */
    private Integer uid;

    /**
     * 学号
     */
    @Column(name = "student_num")
    private String studentNum;

    /**
     * 姓名
     */
    @Column(name = "stu_name")
    private String stuName;

    /**
     * 性别    0-男  1-女
     */
    @Column(name = "stu_sex")
    private Boolean stuSex;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    /**
     * 民族
     */
    private String nation;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status")
    private String politicsStatus;

    /**
     * 入学时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "enter_time")
    private Date enterTime;

    /**
     * 毕业时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "graduate_time")
    private Date graduateTime;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 户口类型   0-农村   1-城市
     */
    private Boolean hukou;

    /**
     * 电话号码
     */
    private String telphone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 微信号
     */
    @Column(name = "we_chat")
    private String weChat;

    /**
     * 籍贯
     */
    @Column(name = "native_place")
    private String nativePlace;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 在校状态
     */
    @Column(name = "state_school")
    private String stateSchool;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 个人描述
     */
    @Column(name = "hoby_des")
    private String hobyDes;

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

    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 是否可以修改 0-不可以修改  1-可以修改
     */
    @Column(name = "is_change")
    private Integer isChange;
}