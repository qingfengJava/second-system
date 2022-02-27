package com.qingfeng.service;

import com.qingfeng.entity.Users;
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

    /**
     * 根据用户Id，修改用户密码
     * @param uid
     * @param password
     * @return
     */
    ResultVO updatePassword(String uid, String password);

    /**
     * 根据用户Id，完善用户信息
     * @param uid
     * @param users
     * @param img
     * @return
     */
    ResultVO updateMessage(String uid, Users users);

    /**
     * 根据用户Id查询用户信息
     * @param uid
     * @return
     */
    ResultVO checkUser(String uid);

    /**
     * 根据用户id查询用户详情信息
     * @param uid
     * @return
     */
    ResultVO checkUserInfo(String uid);
}
