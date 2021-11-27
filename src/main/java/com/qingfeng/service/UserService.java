package com.qingfeng.service;

import com.qingfeng.entity.User;

/**
 * @author 清风学Java
 * @date 2021/10/27
 * @apiNote
 */
public interface UserService {

    /**
     * 用户登录
     * @param username 用户登录时的用户名
     * @param password 用户登录时的密码
     * @return
     */
    User login(String username, String password);

    /**
     * 根据用户名修改密码
     * @param username 用户名
     * @param newPassword 要修改的密码
     */
    void updatePassword(String username, String newPassword);
}
