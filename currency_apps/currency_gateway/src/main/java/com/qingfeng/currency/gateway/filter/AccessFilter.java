package com.qingfeng.currency.gateway.filter;

import com.qingfeng.currency.authority.dto.auth.ResourceQueryDTO;
import com.qingfeng.currency.authority.entity.auth.Resource;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.constant.CacheKey;
import com.qingfeng.currency.context.BaseContextConstants;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.currency.gateway.api.ResourceApi;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权处理过滤器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/20
 */
@Component
@Slf4j
@Order(15)
public class AccessFilter extends BaseFilter {

    @Autowired
    private CacheChannel cacheChannel;
    @Autowired
    private ResourceApi resourceApi;

    /**
     * 验证当前用户是否拥有某个URI的访问权限
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 第1步：判断当前请求uri是否需要忽略
        if (isIgnoreToken(exchange)) {
            return chain.filter(exchange);
        }

        // 第2步：获取当前请求的请求方式和uri，拼接成GET/user/page这种形式，称为权限标识符
        //动态获取当前的url
        ServerHttpRequest request = exchange.getRequest();
        String requestURI = request.getURI().getPath();
        //截取出指定的uri    /api/file/ 之后的内容
//        requestURI = StrUtil.subSuf(requestURI, gatewayPrefix.length());
//        requestURI = StrUtil.subSuf(requestURI, requestURI.indexOf("/", 1));
        String method = request.getMethod().name();
        String permission = method + requestURI;

        // 第3步：从缓存中获取所有需要进行鉴权的资源(同样是由资源表的method字段值+url字段值拼接成)，如果没有获取到则通过Feign调用权限服务获取并放入缓存中
        CacheObject resourceNeed2AuthObject =
                cacheChannel.get(CacheKey.RESOURCE,
                        CacheKey.RESOURCE_NEED_TO_CHECK);
        List<String> resourceNeed2AuthList =
                (List<String>) resourceNeed2AuthObject.getValue();
        if (resourceNeed2AuthList == null) {
            //缓存中没有相应的数据
            resourceNeed2AuthList = resourceApi.list().getData();
            if (resourceNeed2AuthList != null && resourceNeed2AuthList.size() > 0) {
                //放入缓存中
                cacheChannel.set(CacheKey.RESOURCE,
                        CacheKey.RESOURCE_NEED_TO_CHECK,
                        resourceNeed2AuthList);
            }
        }

        // 第4步：判断这些资源是否包含当前请求的权限标识符，如果不包含当前请求的权限标识符，则返回未经授权错误提示
        if (resourceNeed2AuthList != null) {
            long count = resourceNeed2AuthList.stream().filter(r -> {
                return permission.startsWith(r);
            }).count();
            //不包含就是未知请求
            if (count == 0) {
                //直接返回异常
                return out(exchange.getResponse(), ExceptionCode.UNAUTHORIZED.getMsg(),
                        ExceptionCode.UNAUTHORIZED.getCode(), 200);
            }
        }

        // 第5步：如果包含当前的权限标识符，则从header中取出用户id，根据用户id取出
        // 缓存中的用户拥有的权限，如果没有取到则通过Feign调用权限服务获取并放入缓存，判
        // 断用户拥有的权限是否包含当前请求的权限标识符
        String userId = exchange.getResponse().getHeaders().get(BaseContextConstants.JWT_KEY_USER_ID).get(0);
        CacheObject cacheObject = cacheChannel.get(CacheKey.USER_RESOURCE, userId);
        List<String> userResource = (List<String>) cacheObject.getValue();
        // 如果从缓存获取不到当前用户的资源权限，需要查询数据库获取，然后再放入缓存
        if (userResource == null) {
            //缓存中不存在，需要通过接口远程调用权限服务来获取
            ResourceQueryDTO resourceQueryDTO = ResourceQueryDTO
                    .builder()
                    .userId(new Long(userId))
                    .build();
            //通过Feign调用服务，查询当前用户拥有的权限
            R<List<Resource>> result = resourceApi.visible(resourceQueryDTO);
            if (result.getData() != null) {
                List<Resource> userResourceList = result.getData();
                if (userResourceList != null && userResourceList.size() > 0) {
                    userResource = userResourceList.stream().map((Resource r) -> {
                        return r.getMethod() + r.getUrl();
                    }).collect(Collectors.toList());
                    //将当前用户拥有的权限放入缓存
                    cacheChannel.set(CacheKey.USER_RESOURCE, userId, userResource);
                }
            }
        }

        // 第6步：如果用户拥有的权限包含当前请求的权限标识符则说明当前用户拥有权限，直接放行
        long count = userResource.stream().filter((String r) -> {
            return permission.startsWith(r);
        }).count();

        if (count > 0) {
            // 第7步：如果用户拥有的权限不包含当前请求的权限标识符则说明当前用户没有权限，返回未经授权错误提示
            return chain.filter(exchange);
        } else {
            log.warn("用户{}没有访问{}资源的权限", userId, method + requestURI);
            return out(exchange.getResponse(), ExceptionCode.UNAUTHORIZED.getMsg(),
                    ExceptionCode.UNAUTHORIZED.getCode(), 200);
        }
    }

    @Override
    public int getOrder() {
        return 15;
    }
}
