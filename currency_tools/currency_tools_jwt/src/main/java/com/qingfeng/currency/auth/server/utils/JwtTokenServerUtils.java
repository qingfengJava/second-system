package com.qingfeng.currency.auth.server.utils;

import com.qingfeng.currency.auth.server.properties.AuthServerProperties;
import com.qingfeng.currency.auth.utils.JwtHelper;
import com.qingfeng.currency.auth.utils.JwtUserInfo;
import com.qingfeng.currency.auth.utils.Token;
import com.qingfeng.currency.exception.BizException;
import lombok.AllArgsConstructor;

/**
 * jwt token 工具
 *
 * @author 清风学Java
 */
@AllArgsConstructor
public class JwtTokenServerUtils {
    /**
     * 认证服务端使用，如 authority-server
     * 生成和 解析token
     */
    private AuthServerProperties authServerProperties;

    /**
     * 生成token
     * @param jwtInfo
     * @param expire
     * @return
     * @throws BizException
     */
    public Token generateUserToken(JwtUserInfo jwtInfo, Integer expire) throws BizException {
        AuthServerProperties.TokenInfo userTokenInfo = authServerProperties.getUser();
        if (expire == null || expire <= 0) {
            expire = userTokenInfo.getExpire();
        }
        return JwtHelper.generateUserToken(jwtInfo, userTokenInfo.getPriKey(), expire);
    }

    /**
     * 解析token
     * @param token
     * @return
     * @throws BizException
     */
    public JwtUserInfo getUserInfo(String token) throws BizException {
        AuthServerProperties.TokenInfo userTokenInfo = authServerProperties.getUser();
        return JwtHelper.getJwtFromToken(token, userTokenInfo.getPubKey());
    }
}
