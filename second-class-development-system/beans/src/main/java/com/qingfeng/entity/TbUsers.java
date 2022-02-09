package com.qingfeng.entity;

import javax.persistence.*;

@Table(name = "tb_users")
public class TbUsers {
    /**
     * 主键，无实意
     */
    @Id
    @Column(name = "uId")
    private Integer uid;

    /**
     * 账号 默认是学号
     */
    private String username;

    /**
     * 密码 默认是身份证后8位
     */
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "realName")
    private String realname;

    /**
     * 昵称
     */
    @Column(name = "nickName")
    private String nickname;

    /**
     * 电话号码
     */
    @Column(name = "telPhone")
    private String telphone;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像路径
     */
    private String photo;

    /**
     * 是否是管理员，0-学生，1-普通管理员（具有权限的学生和老师），2-终极管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

    /**
     * 是否删除，0-未删除，1-删除
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 兴趣描述
     */
    @Column(name = "hobyDes")
    private String hobydes;

    /**
     * 获取主键，无实意
     *
     * @return uId - 主键，无实意
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置主键，无实意
     *
     * @param uid 主键，无实意
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取账号 默认是学号
     *
     * @return username - 账号 默认是学号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账号 默认是学号
     *
     * @param username 账号 默认是学号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码 默认是身份证后8位
     *
     * @return password - 密码 默认是身份证后8位
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码 默认是身份证后8位
     *
     * @param password 密码 默认是身份证后8位
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取真实姓名
     *
     * @return realName - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取昵称
     *
     * @return nickName - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取电话号码
     *
     * @return telPhone - 电话号码
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * 设置电话号码
     *
     * @param telphone 电话号码
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * 获取QQ号
     *
     * @return qq - QQ号
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置QQ号
     *
     * @param qq QQ号
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取头像路径
     *
     * @return photo - 头像路径
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置头像路径
     *
     * @param photo 头像路径
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 获取是否是管理员，0-学生，1-普通管理员（具有权限的学生和老师），2-终极管理员
     *
     * @return is_admin - 是否是管理员，0-学生，1-普通管理员（具有权限的学生和老师），2-终极管理员
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否是管理员，0-学生，1-普通管理员（具有权限的学生和老师），2-终极管理员
     *
     * @param isAdmin 是否是管理员，0-学生，1-普通管理员（具有权限的学生和老师），2-终极管理员
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取是否删除，0-未删除，1-删除
     *
     * @return is_delete - 是否删除，0-未删除，1-删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除，0-未删除，1-删除
     *
     * @param isDelete 是否删除，0-未删除，1-删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取兴趣描述
     *
     * @return hobyDes - 兴趣描述
     */
    public String getHobydes() {
        return hobydes;
    }

    /**
     * 设置兴趣描述
     *
     * @param hobydes 兴趣描述
     */
    public void setHobydes(String hobydes) {
        this.hobydes = hobydes;
    }
}