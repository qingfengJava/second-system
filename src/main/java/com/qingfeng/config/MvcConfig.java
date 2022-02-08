package com.qingfeng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置thymeleaf放行规则
 *
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
        registry.addViewController("login.html").setViewName("login");

        //初始界面
        registry.addViewController("index/top.html").setViewName("index/top");
        registry.addViewController("index/left.html").setViewName("index/left");
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("index/index.html").setViewName("index/index");

        //信息管理界面
        registry.addViewController("data/change-password.html").setViewName("data/change-password");
        registry.addViewController("data/complete-information.html").setViewName("data/complete-information");
        registry.addViewController("data/data-form.html").setViewName("data/data-form");
        registry.addViewController("data/personal-center.html").setViewName("data/personal-center");
    }
}
