package com.qingfeng.currency.gateway.config;

import com.qingfeng.currency.context.BaseContextConstants;
import feign.RequestInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.stream.Collectors;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/20
 */
@Configuration
public class FeignConfig {

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

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
