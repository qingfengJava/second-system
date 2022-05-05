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
 * @author 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_users")
public class Users {
    /**
     * 主键，无实意
     */
    @Id
    @Column(name = "uId")
    @ExcelIgnore
    private Integer uid;

    /**
     * 账号 默认是学号   社团用户可以自定义名字
     */
    @ExcelProperty(value = "学生学号/账号",index = 0)
    private String username;

    /**
     * 密码 默认是身份证后8位
     */
    @ExcelProperty(value = "用户密码",index = 1)
    private String password;
    /**
     * 加密的盐值
     */
    @ExcelProperty(value = "加密盐值",index = 2)
    private String salt;

    /**
     * 真实姓名
     */
    @Column(name = "realName")
    @ExcelProperty(value = "真实姓名",index = 3)
    private String realname;

    /**
     * 昵称
     */
    @Column(name = "nickName")
    @ExcelProperty(value = "昵称",index = 4)
    private String nickname;

    /**
     * 电话号码
     */
    @Column(name = "telPhone")
    @ExcelProperty(value = "电话号码",index = 5)
    private String telphone;

    /**
     * QQ号
     */
    @ExcelProperty(value = "QQ号",index = 6)
    private String qq;

    /**
     * 电子邮箱
     */
    @ExcelProperty(value = "电子邮箱",index = 7)
    private String email;

    /**
     * 头像路径
     */
    @ExcelProperty(value = "头像路径",index = 8)
    private String photo;

    /**
     * 用户身份： 1：学生  2：学生社团工作部  3:校团委   4：校领导   5：超级管理员
     */
    @Column(name = "is_admin")
    @ExcelProperty(value = "用户身份",index = 9)
    private Integer isAdmin;

    /**
     * 是否删除，0-未删除，1-删除
     */
    @Column(name = "is_delete")
    @ExcelProperty(value = "是否删除",index = 10)
    private Integer isDelete;

    /**
     * 兴趣描述
     */
    @Column(name = "hobyDes")
    @ExcelProperty(value = "兴趣描述",index = 11)
    private String hobydes;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    @ExcelIgnore
    private Date createTime;

}