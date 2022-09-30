package com.qingfeng.currency.authority.controller.auth;

import com.qingfeng.currency.authority.biz.service.auth.ValidateCodeService;
import com.qingfeng.currency.authority.biz.service.auth.impl.AuthManager;
import com.qingfeng.currency.authority.dto.auth.LoginDTO;
import com.qingfeng.currency.authority.dto.auth.LoginParamDTO;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
@Api(value = "登录（认证）控制器", tags = "登录（认证）控制器")
@Slf4j
@RestController
@RequestMapping("/anno")
public class LoginController extends BaseController {

    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private AuthManager authManager;

    @ApiOperation(value = "生成验证码", notes = "为前端系统生成验证码")
    @GetMapping(value = "/captcha", produces = "image/png")
    @SysLog("生成验证码")
    public void captcha(@ApiParam(value = "用户身份的标识key", required = true)
                        @RequestParam(value = "key") String key,
                        HttpServletResponse response) throws IOException {
        validateCodeService.create(key, response);
    }

    @ApiOperation(value = "登录认证", notes = "登录认证")
    @PostMapping(value = "/login")
    @SysLog("登录认证")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO login) throws BizException {
        log.info("account={}", login.getAccount());
        //判断验证码校验是否通过
        if (this.validateCodeService.check(login.getKey(), login.getCode())) {
            //验证码校验通过，执行具体的登录认证逻辑
            return authManager.login(login.getAccount(), login.getPassword());
        }
        return this.success(null);
    }

    @ApiOperation(value = "校验验证码", notes = "校验验证码")
    @PostMapping(value = "/check")
    public boolean check(@RequestBody LoginParamDTO login){
        return validateCodeService.check(login.getKey(), login.getCode());
    }
}
