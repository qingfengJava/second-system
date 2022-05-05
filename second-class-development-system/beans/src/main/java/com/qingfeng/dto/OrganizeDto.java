package com.qingfeng.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 社团组织实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrganizeDto {

    @ExcelProperty(value = "社团管理员账号",index = 0)
    private String username;

    /**
     * 密码 默认是身份证后8位
     */
    @ExcelProperty(value = "社团管理员密码",index = 1)
    private String password;
    /**
     * 加密的盐值
     */
    @ExcelProperty(value = "加密盐值",index = 2)
    private String salt;

    /**
     * 电话号码
     */
    @ExcelProperty(value = "电话号码",index = 3)
    private String telphone;

    /**
     * 电子邮箱
     */
    @ExcelProperty(value = "电子邮箱",index = 4)
    private String email;

    /**
     * 头像路径
     */
    @ExcelProperty(value = "头像路径",index = 5)
    private String photo;

    /**
     * 用户身份： 1：学生  2：学生社团工作部  3:校团委   4：校领导   5：超级管理员
     */
    @ExcelProperty(value = "社团管理员身份",index =6)
    private Integer isAdmin;



    /**
     * 社团用户id
     */
    @ExcelProperty(value = "社团管理员id", index = 7)
    private Integer userId;

    /**
     * 社团名称
     */
    @ExcelProperty(value = "社团名称", index = 8)
    private String organizeName;

    /**
     * 社团所属部分
     */
    @ExcelProperty(value = "社团所属部分", index = 9)
    private String organizeDepartment;

    /**
     * 社团介绍
     */
    @ExcelProperty(value = "社团介绍", index = 10)
    private String organizeIntroduce;

    /**
     * 社团成立时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "社团成立时间", index = 11)
    private Date birthTime;

    /**
     * 是否删除（0：未删除   1：已删除）
     */
    @ExcelProperty(value = "是否删除", index = 12)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty(value = "创建时间", index = 13)
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty(value = "更新时间", index = 14)
    private Date updateTime;
}