package com.qingfeng.config;

import com.qingfeng.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/04/18
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    /**
     * 注意要把Swagger给放行
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(checkTokenInterceptor)
                //设置所有要拦截的信息
                .addPathPatterns("/active/**")
                .addPathPatterns("/active-apply/**")
                .addPathPatterns("/evaluation/**")
                .addPathPatterns("/grade/**")
                .addPathPatterns("/leader/**")
                .addPathPatterns("/notice/**")
                .addPathPatterns("/organize/**")
                .addPathPatterns("/quality/**")
                .addPathPatterns("/active-regist/**")
                .addPathPatterns("/task/**")
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/analysis/**");
    }
}