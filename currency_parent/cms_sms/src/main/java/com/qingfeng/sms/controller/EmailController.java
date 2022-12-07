package com.qingfeng.sms.controller;

import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.log.annotation.SysLog;
import com.qingfeng.sms.entity.EmailEntity;
import com.qingfeng.sms.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
@Api(value = "提供文件上传的相关接口", tags = "文件上传服务")
@RestController
@RequestMapping("/email")
@Validated
@Slf4j
public class EmailController extends BaseController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "发送邮件信息", notes = "发送邮件信息")
    @PostMapping("/send")
    @SysLog("发送邮件信息")
    public Integer sendEmail(@ApiParam(value = "邮件信息实体", required = true)
                             @RequestBody @Validated EmailEntity emailEntity) {
        return emailService.sendEmail(emailEntity);
    }

}
