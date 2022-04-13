package com.qingfeng.constant;

/**
 * 定义一个类，存储我们的响应状态码。
 *
 * @author 清风学Java
 * @date 2021/11/17
 * @apiNote
 */
public class ResStatus {

    public static final int OK = 10000;
    public static final int NO = 10001;

    /**
     * 登录认证成功
     */
    public static final int LOGIN_SUCCESS=2000;
    /**
     * 用户名错误
     */
    public static final int LOGIN_FAIL_USERNAME=2001;
    /**
     * 用户密码错误
     */
    public static final int LOGIN_FAIL_PASSWORD=2002;
}
