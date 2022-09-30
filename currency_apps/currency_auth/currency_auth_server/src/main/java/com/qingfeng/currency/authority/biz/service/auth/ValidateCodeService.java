package com.qingfeng.currency.authority.biz.service.auth;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface ValidateCodeService {

    /**
     * 根据用户身份标识生成验证码
     * @param key
     * @param response
     * @throws IOException
     */
    public void create(String key, HttpServletResponse response) throws IOException;

    /**
     * 校验用户提供的验证码是否正确
     * @param key
     * @param code
     * @return
     */
    boolean check(String key, String code);
}
