package com.qingfeng.config;

import com.qingfeng.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 完成处理器拦截器的注册
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/1/16
 */
@Configuration
public class LoginInterceptorConfigurer  implements WebMvcConfigurer {

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //完成拦截器的注册
        registry.addInterceptor(new LoginInterceptor())
                // 表示要拦截的url是什么
                .addPathPatterns("/**")
                // 表示要放行的url是什么
                .excludePathPatterns("/fonts/**","/css/**","/images/**",
                        "/js/**","/img/**","/plugins/**","/login.html","/userLogin/**","/login");
    }
}
