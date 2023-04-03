package com.qingfeng.cms.controller.front;

import com.qingfeng.cms.biz.sign.service.ActiveSignService;
import com.qingfeng.cms.domain.sign.vo.UserActiveSignFrontVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/3
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供签到服务需要的接口", tags = "提供签到服务需要的接口")
@RequestMapping("/anno/front")
public class ActiveFrontController extends BaseController {

    @Autowired
    private ActiveSignService activeSignService;

    @ApiOperation(value = "查询学生报名的活动，按活动时间正序排序，已结束的活动排在最后")
    @GetMapping("/user/sign/{userId}")
    public R<List<UserActiveSignFrontVo>> userSignActive(@PathVariable("userId") Long userId) {
        List<UserActiveSignFrontVo> userActiveSignFrontVoList = activeSignService.findUserSignActiveForFront(userId);
        return success(userActiveSignFrontVoList);
    }

    // TODO 查询社团申请并已经通过的活动，并且按照活动开始时间正序排序


    /**
     * 单独签到的服务调用SDK
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "进行活动一键签到", notes = "进行活动一键签到")
    @GetMapping("/sign/check/{userId}")
    @SysLog("进行活动一键签到")
    public R oneClickCheckIn(@PathVariable("userId") Long userId) {
        // TODO 活动签到
        return success();
    }
}
