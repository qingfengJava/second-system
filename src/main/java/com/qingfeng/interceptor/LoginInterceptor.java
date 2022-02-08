package com.qingfeng.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器
 *
 * @author 清风学Java
 * @date 2021/10/7
 * @apiNote
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象是否有user，如果有则放行，如果没有则重定向到登录页面
     * @param request  请求对象
     * @param response 响应处理
     * @param handler 处理器(url+Controller：映射)
     * @return  如果返回值为true表示放行当前的请求。如果返回值为false表示拦截当前的请求。
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //通过HttpServletRequest对象来获取session对象
        Object obj = request.getSession().getAttribute("user");
        if (obj == null){
            //说明用户没有登录过系统，则重定向到login.html页面
            response.sendRedirect("/login");
            //结束后续的调用
            return false;
        }

        //请求放行
        return true;
    }
}
