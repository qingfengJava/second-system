package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户实体类
 *
 * @author 清风学Java
 * @date 2021/10/27
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    /**
     * 主键，无实意
     */
    private Integer uid;
    /**
     * 账号，默认是学号
     */
    private String username;
    /**
     * 密码，默认是身份证后8位
     */
    private String password;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 电话号码
     */
    private String telPhone;
    /**
     * QQ
     */
    private String qq;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 是否是管理员，0-学生，1-普通管理员，2-终极管理员
     */
    private Integer isAdmin;
    /**
     * 是否删除，0-未删除，1-删除
     */
    private Integer isDelete;
    /**
     * 兴趣描述
     */
    private String hobyDes;
}
