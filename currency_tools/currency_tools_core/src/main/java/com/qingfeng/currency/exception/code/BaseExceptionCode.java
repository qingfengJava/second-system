package com.qingfeng.currency.exception.code;

/**
 *
 * @author 清风学Java
 */
public interface BaseExceptionCode {
    /**
     * 异常编码
     *
     * @return
     */
    int getCode();

    /**
     * 异常消息
     * @return
     */
    String getMsg();
}
