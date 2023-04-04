package com.qingfeng.cms.controller.front;

import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.sign.service.ActiveSignService;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.vo.ActiveApplyVo;
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
    @Autowired
    private ApplyService applyService;

    @ApiOperation(value = "查询学生报名的活动，按活动时间正序排序，已结束的活动排在最后", notes = "查询学生报名的活动，按活动时间正序排序，已结束的活动排在最后")
    @GetMapping("/user/sign/{userId}")
    @SysLog("查询学生报名的活动，按活动时间正序排序，已结束的活动排在最后")
    public R<List<UserActiveSignFrontVo>> userSignActive(@PathVariable("userId") Long userId) {
        List<UserActiveSignFrontVo> userActiveSignFrontVoList = activeSignService.findUserSignActiveForFront(userId);
        return success(userActiveSignFrontVoList);
    }

    @ApiOperation(value = "根据活动Id和用户Id查询活动信息", notes = "根据活动Id和用户Id查询活动信息")
    @GetMapping("/active/{applyId}/{userId}")
    @SysLog("根据活动Id和用户Id查询活动信息")
    public R<ActiveApplyVo> findActiveByApplyIdAndUserId(@PathVariable("applyId") Long applyId,
                                                @PathVariable("userId") Long userId){
        ActiveApplyVo activeApplyVo = applyService.findActiveByApplyIdAndUserId(applyId, userId);
        return success(activeApplyVo);
    }

    /**
     * 单独签到的服务调用SDK
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "进行活动一键签到", notes = "进行活动一键签到")
    @GetMapping("/sign/check/{userId}/{applyId}")
    @SysLog("进行活动一键签到")
    public R oneClickCheckIn(@PathVariable("userId") Long userId,
                             @PathVariable("applyId") Long applyId) {
        // 活动签到
        activeSignService.oneClickCheckIn(userId, applyId);
        return success();
    }

    @ApiOperation(value = "查询社团申请并已经通过的活动，并且按照活动开始时间正序排序", notes = "查询社团申请并已经通过的活动，并且按照活动开始时间正序排序")
    @GetMapping("/apply/{userId}")
    @SysLog("查询社团申请并已经通过的活动，并且按照活动开始时间正序排序")
    public R<List<ApplyEntity>> applyByUserId(@PathVariable("userId") Long userId){
        List<ApplyEntity> applyList = applyService.applyByUserId(userId);
        return success(applyList);
    }

    @ApiOperation(value = "发布活动开始通知", notes = "发布活动开始通知")
    @GetMapping("/apply/start/{applyId}")
    @SysLog("发布活动开始通知")
    public R applyStartNotice(@PathVariable("applyId") Long applyId){
        applyService.applyStartNotice(applyId);
        return success();
    }
}
