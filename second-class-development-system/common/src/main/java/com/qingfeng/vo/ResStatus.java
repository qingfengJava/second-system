package com.qingfeng.vo;

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
     * 用户未登录
     */
    public static final int LOGIN_FAIL_NOT=2001;
    /**
     * 用户登录失败
     */
    public static final int LOGIN_FAIL_OVERDUE=2002;
}
