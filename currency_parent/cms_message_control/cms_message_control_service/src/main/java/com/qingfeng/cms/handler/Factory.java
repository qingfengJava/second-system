package com.qingfeng.cms.handler;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂设计模式
 * @author Administrator
 */
public class Factory {

    private static Map<String,AbstractHandler> strategyMap = new HashMap<>();

    public static AbstractHandler getInvokeStrategy(String str){
        return strategyMap.get(str);
    }

    public static  void register(String str ,AbstractHandler handler){
        if (StringUtils.isEmpty(str)|| null==handler) {
            return;
        }
        strategyMap.put(str,handler);
    }

}
