package com.qingfeng.currency.gateway.filter;

import com.qingfeng.currency.auth.client.properties.AuthClientProperties;
import com.qingfeng.currency.auth.client.utils.JwtTokenClientUtils;
import com.qingfeng.currency.auth.utils.JwtUserInfo;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.context.BaseContextConstants;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.utils.StrHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/19
 */
@Component
@Order(5)
public class TokenContextFilter extends BaseFilter{

    @Autowired
    private AuthClientProperties authClientProperties;
    @Autowired
    private JwtTokenClientUtils jwtTokenClientUtils;

    /**
     * 执行过滤的流程
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //首先判断我们的请求路径是否是要放行的
        if (isIgnoreToken(exchange)) {
            return chain.filter(exchange);
        }

        //获取token解析，然后将信息放入header
        ServerHttpRequest request = exchange.getRequest();
        //1、获取token
        String userToken = request.getHeaders().get(authClientProperties.getUser().getHeaderName()).get(0);
        //2, 解析token（jwt令牌）
        JwtUserInfo userInfo = null;
        try {
            userInfo = jwtTokenClientUtils.getUserInfo(userToken);
        } catch (BizException e) {
            return out(exchange.getResponse(), e.getMessage(), e.getCode(), 200);
        } catch (Exception e) {
            return out(exchange.getResponse(), "解析token出错", R.FAIL_CODE, 200);
        }

        //3, 将解析出的用户信息放入zuul的header中
        if (userInfo != null) {
            addHeader(exchange, BaseContextConstants.JWT_KEY_ACCOUNT,
                    userInfo.getAccount());
            addHeader(exchange, BaseContextConstants.JWT_KEY_USER_ID,
                    userInfo.getUserId());
            addHeader(exchange, BaseContextConstants.JWT_KEY_NAME,
                    userInfo.getName());
            addHeader(exchange, BaseContextConstants.JWT_KEY_ORG_ID,
                    userInfo.getOrgId());
            addHeader(exchange, BaseContextConstants.JWT_KEY_STATION_ID,
                    userInfo.getStationId());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //优先级设置大一点，需要在gateway路由之后执行
        return 5;
    }

    /**
     * 将用户信息设置到请求头中
     * @param exchange
     * @param name
     * @param value
     */
    private void addHeader(ServerWebExchange exchange, String name, Object value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(name, StrHelper.encode(value.toString()));
    }
}
