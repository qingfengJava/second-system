package com.qingfeng.cms.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * 策略者设计模式
 *
 * @author 清风学Java
 */
public interface Handler extends InitializingBean {

    /**
     * 方法执行器
     * @param nikeName
     * @return
     */
    Object execute(String nikeName);
}
