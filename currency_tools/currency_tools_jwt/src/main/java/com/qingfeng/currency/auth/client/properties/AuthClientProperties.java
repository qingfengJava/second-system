package com.qingfeng.currency.auth.client.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.qingfeng.currency.auth.client.properties.AuthClientProperties.PREFIX;

/**
 * 客户端认证配置
 * @author 清风学Java
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@NoArgsConstructor
public class AuthClientProperties {
    public static final String PREFIX = "authentication";
    private TokenInfo user;
    @Data
    public static class TokenInfo {
        /**
         * 请求头名称
         */
        private String headerName;
        /**
         * 解密 网关使用
         */
        private String pubKey;
    }
}
