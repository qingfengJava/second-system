package com.qingfeng.cms.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * 抽象的策略对象——模板方法类
 *
 * @author 清风学Java
 */
public abstract class AbstractHandler implements InitializingBean {

    /**
     * 普通的执行器
     * @param nikeName
     * @return
     */
    public Object execute(String nikeName) {
        // 只有继承子类重写他才能使用
        throw new UnsupportedOperationException();
    }
}
