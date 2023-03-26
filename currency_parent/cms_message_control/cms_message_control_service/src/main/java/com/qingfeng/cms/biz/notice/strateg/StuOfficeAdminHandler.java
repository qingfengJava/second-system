package com.qingfeng.cms.biz.notice.strateg;

import com.qingfeng.cms.handler.AbstractHandler;
import com.qingfeng.cms.handler.Factory;
import org.springframework.stereotype.Component;

/**
 * 学生处策略执行器
 *
 * @author 清风学Java
 */
@Component
public class StuOfficeAdminHandler extends AbstractHandler {

    @Override
    public Object execute(String nikeName) {
        //业务逻辑

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("",this);
    }
}
