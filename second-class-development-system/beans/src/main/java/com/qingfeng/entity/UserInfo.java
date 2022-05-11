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
    @ExcelIgnore
    private Integer userInfoId;

    /**
     * 学生表的外键，关联学生表信息
     */
    @ExcelProperty(value = "用户主键Id",index = 0)
    private Integer uid;

    /**
     * 学号
     */
    @Column(name = "student_num")
    @ExcelProperty(value = "学生学号",index = 1)
    private String studentNum;

    /**
     * 学生类型
     */
    @ExcelProperty(value = "学生类型",index = 2)
    private String type;

    /**
     * 姓名
     */
    @Column(name = "stu_name")
    @ExcelProperty(value = "学生姓名",index = 3)
    private String stuName;

    /**
     * 性别    0-男  1-女
     */
    @Column(name = "stu_sex")
    @ExcelProperty(value = "学生性别",index = 4)
    private String stuSex;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "出生日期",index = 5)
    private Date birth;

    /**
     * 民族
     */
    @ExcelProperty(value = "名族",index = 6)
    private String nation;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status")
    @ExcelProperty(value = "政治面貌",index = 7)
    private String politicsStatus;

    /**
     * 入学时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "enter_time")
    @ExcelProperty(value = "入学时间",index = 8)
    private Date enterTime;

    /**
     * 毕业时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "graduate_time")
    @ExcelProperty(value = "毕业时间",index = 9)
    private Date graduateTime;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    @ExcelProperty(value = "身份证号",index = 10)
    private String idCard;

    /**
     * 户口类型   0-农村   1-城市
     */
    @ExcelProperty(value = "户口类型",index = 11)
    private String hukou;

    /**
     * 电话号码
     */
    @ExcelProperty(value = "电话号码",index = 12)
    private String telphone;

    /**
     * 电子邮箱
     */
    @ExcelProperty(value = "电子邮箱",index = 13)
    private String email;

    /**
     * QQ号
     */
    @ExcelProperty(value = "QQ号",index = 14)
    private String qq;

    /**
     * 微信号
     */
    @Column(name = "we_chat")
    @ExcelProperty(value = "微信号",index = 15)
    private String weChat;

    /**
     * 籍贯
     */
    @Column(name = "native_place")
    @ExcelProperty(value = "籍贯",index = 16)
    private String nativePlace;

    /**
     * 家庭地址
     */
    @ExcelProperty(value = "家庭地址",index = 17)
    private String address;

    /**
     * 在校状态
     */
    @Column(name = "state_school")
    @ExcelProperty(value = "在校状态",index = 18)
    private String stateSchool;

    /**
     * 院系
     */
    @ExcelProperty(value = "所属院系",index = 19)
    private String department;

    /**
     * 专业
     */
    @ExcelProperty(value = "所属专业",index = 20)
    private String major;

    /**
     * 年级
     */
    @ExcelProperty(value = "年级",index = 21)
    private String grade;

    /**
     * 班级
     */
    @ExcelProperty(value = "班级",index = 22)
    private String clazz;

    /**
     * 学制
     */
    @Column(name = "educational_system")
    @ExcelProperty(value = "学制",index = 23)
    private Integer educationalSystem;

    /**
     * 个人描述
     */
    @Column(name = "hoby_des")
    @ExcelProperty(value = "个人描述",index = 24)
    private String hobyDes;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    @ExcelProperty(value = "创建时间",index = 25)
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    @ExcelProperty(value = "更新时间",index = 26)
    private Date updateTime;

    @Column(name = "is_delete")
    @ExcelProperty(value = "是否删除",index = 27)
    private Integer isDelete;

    /**
     * 是否可以修改 0-不可以修改  1-可以修改
     */
    @Column(name = "is_change")
    @ExcelProperty(value = "是否可以修改",index = 28)
    private Integer isChange;
}