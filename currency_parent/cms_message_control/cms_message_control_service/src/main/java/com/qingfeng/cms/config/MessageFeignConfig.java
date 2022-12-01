package com.qingfeng.cms.config;

import com.qingfeng.currency.context.BaseContextConstants;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * OpenFeign请求调用时，封装请求头
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/1
 */
@Configuration
public class MessageFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return (requestTemplate) -> {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if(requestAttributes != null){
                    //同步到请求中
                    requestTemplate.header(BaseContextConstants.JWT_KEY_ACCOUNT,
                            requestAttributes.getRequest().getHeader(BaseContextConstants.JWT_KEY_ACCOUNT));
                    requestTemplate.header(BaseContextConstants.JWT_KEY_USER_ID,
                            requestAttributes.getRequest().getHeader(BaseContextConstants.JWT_KEY_USER_ID));
                    requestTemplate.header(BaseContextConstants.JWT_KEY_NAME,
                            requestAttributes.getRequest().getHeader(BaseContextConstants.JWT_KEY_NAME));
                    requestTemplate.header(BaseContextConstants.JWT_KEY_ORG_ID,
                            requestAttributes.getRequest().getHeader(BaseContextConstants.JWT_KEY_ORG_ID));
                    requestTemplate.header(BaseContextConstants.JWT_KEY_STATION_ID,
                            requestAttributes.getRequest().getHeader(BaseContextConstants.JWT_KEY_STATION_ID));
                    return;
                }
        };
    }

}
