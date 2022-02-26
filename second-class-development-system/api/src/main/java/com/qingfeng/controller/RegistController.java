package com.qingfeng.controller;

import com.qingfeng.entity.Regist;
import com.qingfeng.service.RegistService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/15
 */
@RestController
@RequestMapping("/active-regist")
@Api(value = "提供活动报名相关的接口功能",tags = "活动报名")
@CrossOrigin
public class RegistController {

    @Autowired
    private RegistService registService;

    @ApiOperation("活动报名接口")
    @PostMapping("/registration/{applyId}")
    public ResultVO registrationActive(@PathVariable("applyId") Integer applyId, @RequestBody Regist regist){
        //将用户Id存入apply对象中
        return registService.registrationActive(applyId,regist);
    }

    @ApiOperation("活动报名审核")
    @PostMapping("/check/{activeRegId}")
    public ResultVO checkIsSuccess(@PathVariable("activeRegId") Integer activeRegId,@RequestParam("status") Integer status){
        //对报名参与者的身份进行审核（审核组织者）
        return registService.checkIsSuccess(activeRegId,status);
    }

    @ApiOperation("活动报名删除")
    @PostMapping("/delete/{activeRegId}")
    public ResultVO deleteRegistration(@PathVariable("activeRegId") Integer activeRegId){
        //根据报名表的主键Id对学生报名信息进行删除
        return registService.deleteRegistration(activeRegId);
    }

}
