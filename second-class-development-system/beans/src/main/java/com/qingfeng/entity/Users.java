package com.qingfeng.entity;

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
    private Integer uid;

    /**
     * 账号 默认是学号   社团用户可以自定义名字
     */
    @ExcelProperty("学生学号/账号")
    private String username;

    /**
     * 密码 默认是身份证后8位
     */
    @ExcelProperty("用户密码")
    private String password;
    /**
     * 加密的盐值
     */
    @ExcelProperty("加密盐值")
    private String salt;

    /**
     * 真实姓名
     */
    @Column(name = "realName")
    @ExcelProperty("真实姓名")
    private String realname;

    /**
     * 昵称
     */
    @Column(name = "nickName")
    @ExcelProperty("昵称")
    private String nickname;

    /**
     * 电话号码
     */
    @Column(name = "telPhone")
    @ExcelProperty("电话号码")
    private String telphone;

    /**
     * QQ号
     */
    @ExcelProperty("QQ号")
    private String qq;

    /**
     * 电子邮箱
     */
    @ExcelProperty("电子邮箱")
    private String email;

    /**
     * 头像路径
     */
    @ExcelProperty("头像路径")
    private String photo;

    /**
     * 用户身份： 1：学生  2：学生社团工作部  3:校团委   4：校领导   5：超级管理员
     */
    @Column(name = "is_admin")
    @ExcelProperty("用户身份")
    private Integer isAdmin;

    /**
     * 是否删除，0-未删除，1-删除
     */
    @Column(name = "is_delete")
    @ExcelProperty("是否删除")
    private Integer isDelete;

    /**
     * 兴趣描述
     */
    @Column(name = "hobyDes")
    @ExcelProperty("兴趣描述")
    private String hobydes;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

}