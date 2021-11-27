package com.qingfeng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 清风学Java
 * @date 2021/7/31
 * @apiNote
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 通过这里面的配置，就不需要为每一个访问thymeleaf模板页面单独开发一个controller请求了
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //viewController 请求路径   viewName：跳转视图
        registry.addViewController("login").setViewName("login");
    }
}
