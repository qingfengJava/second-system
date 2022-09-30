package com.qingfeng.currency.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.adapter.IgnoreTokenConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 基础网关过滤器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/19
 */
@Slf4j
public abstract class BaseFilter implements GlobalFilter, Ordered {

    /**
     * 动态的获取请求路径
     */
    @Value("${server.servlet.context-path}")
    protected String gatewayPrefix;

    /**
     * 判断当前请求uri是否需要忽略（直接放行）
     */
    protected boolean isIgnoreToken(ServerWebExchange exchange) {
        //动态获取当前的url
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getPath();
        //截取出指定的uri    /api/file/ 之后的内容
//        uri = StrUtil.subSuf(uri, gatewayPrefix.length());
//        uri = StrUtil.subSuf(uri, uri.indexOf("/", 1));
        //判断是不是我们定义的需要放行的请求路径
        boolean ignoreToken = IgnoreTokenConfig.isIgnoreToken(uri);
        return ignoreToken;
    }


    /**
     * 网关抛异常，不再进行路由，而是直接返回到前端
     * @param response
     * @return
     */
    protected Mono<Void> out(ServerHttpResponse response, String errMsg, int errCode, int httpStatusCode) {
        R tokenError = R.fail(errCode, errMsg);
        response.setStatusCode(HttpStatus.valueOf(httpStatusCode));
        byte[] bits = JSONObject.toJSONString(tokenError).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
