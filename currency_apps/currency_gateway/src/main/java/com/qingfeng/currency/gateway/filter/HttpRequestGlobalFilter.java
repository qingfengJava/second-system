package com.qingfeng.currency.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 处理gateway网关请求体中的内容
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/20
 */
@Slf4j
@Component
@Order(1)
public class HttpRequestGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUrl = request.getPath().toString();
        String requestMethod = request.getMethodValue();
        if (HttpMethod.POST.toString().equals(requestMethod) || HttpMethod.PUT.toString().equals(requestMethod)) {
            return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                String postRequestBodyStr = new String(bytes, StandardCharsets.UTF_8);
                if (requestUrl.contains("/web/api/file")) {
                    log.debug("\n 请求url:`{}` \n 请求类型：{} \n 文件上传", requestUrl, requestMethod);
                } else {
                    log.debug("\n 请求url:`{}` \n 请求类型：{} \n 请求参数：{}", requestUrl, requestMethod, postRequestBodyStr);
                }
                exchange.getAttributes().put("POST_BODY", postRequestBodyStr);
                DataBufferUtils.release(dataBuffer);
                Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return Mono.just(buffer);
                });
                // 下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
                ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return cachedFlux;
                    }
                };
                // 封装request，传给下一级
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            });
        } else if (HttpMethod.GET.toString().equals(requestMethod)
                || HttpMethod.DELETE.toString().equals(requestMethod))

        {
            MultiValueMap<String, String> getRequestParams = request.getQueryParams();
            log.debug("\n 请求url:`{}` \n 请求类型：{} \n 请求参数：{}", requestUrl, requestMethod, getRequestParams);
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

}

