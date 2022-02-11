package com.qingfeng.service;

import com.qingfeng.vo.ResultVO;

/**
 * 用户业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/10
 */
public interface UserService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    ResultVO checkLogin(String username, String password);

    /**
     * 添加用户
     * @param username
     * @param password
     * @param isAdmin
     * @return
     */
    ResultVO userAdd(String username, String password, int isAdmin);
}
