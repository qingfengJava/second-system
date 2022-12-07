package com.qingfeng.sms.controller;

import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.sms.entity.ShortMessageEntity;
import com.qingfeng.sms.service.ShortMessageService;
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
@Api(value = "提供短信发送的相关接口", tags = "短信发送服务")
@RestController
@RequestMapping("/sms")
@Validated
@Slf4j
public class ShortMessageController extends BaseController {

    @Autowired
    private ShortMessageService shortMessageService;

    @ApiOperation(value = "使用腾讯云发送短信", notes = "使用腾讯云发送短信")
    @PostMapping("/send_message")
    private R sendSgortMessage(@ApiParam(value = "短信发送信息实体", required = true)
                               @RequestBody @Validated ShortMessageEntity shortMessageEntity){
        shortMessageService.sendMessage(shortMessageEntity);
        return success();
    }
}
