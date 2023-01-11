package com.qingfeng.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.cms.biz.feedback.service.SystemFeedbackService;
import com.qingfeng.cms.domain.feedback.dto.SystemFeedbackQueryDTO;
import com.qingfeng.cms.domain.feedback.dto.SystemFeedbackSaveDTO;
import com.qingfeng.cms.domain.feedback.entity.SystemFeedbackEntity;
import com.qingfeng.cms.domain.feedback.vo.UserLeaderVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 系统反馈表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供系统反馈的相关功能", tags = "系统反馈")
@RequestMapping("/systemfeedback")
public class SystemFeedbackController extends BaseController {

    @Autowired
    private SystemFeedbackService systemFeedbackService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "15"),
    })
    @ApiOperation(value = "查询个人对系统反馈的信息列表", notes = "查询个人对系统反馈的信息列表")
    @PostMapping("/list")
    @SysLog("查询个人对系统反馈的信息列表")
    public R<IPage<SystemFeedbackEntity>> list(@RequestBody SystemFeedbackQueryDTO systemFeedbackQueryDTO) {
        IPage<SystemFeedbackEntity> page = getPage();

        LbqWrapper<SystemFeedbackEntity> query = Wraps.lbQ(new SystemFeedbackEntity())
                .eq(SystemFeedbackEntity::getUserId, getUserId());
        if (ObjectUtil.isNotEmpty(systemFeedbackQueryDTO.getSystemEvaluation())) {
            query.eq(SystemFeedbackEntity::getSystemEvaluation, systemFeedbackQueryDTO.getSystemEvaluation());
        }
        if (ObjectUtil.isNotEmpty(systemFeedbackQueryDTO.getFeedbackType())) {
            query.eq(SystemFeedbackEntity::getFeedbackType, systemFeedbackQueryDTO.getFeedbackType());
        }
        if (ObjectUtil.isNotEmpty(systemFeedbackQueryDTO.getIsReceive())) {
            query.eq(SystemFeedbackEntity::getIsReceive, systemFeedbackQueryDTO.getIsReceive());
        }

        systemFeedbackService.page(page, query);
        return success(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SystemFeedbackEntity systemFeedback = systemFeedbackService.getById(id);

        return success();
    }

    @ApiOperation(value = "保存系统反馈的信息", notes = "保存系统反馈的信息")
    @PostMapping
    @SysLog("保存系统反馈的信息")
    public R save(@ApiParam(value = "系统反馈信息保存实体", required = true)
                  @RequestBody @Validated SystemFeedbackSaveDTO systemFeedbackSaveDTO) {
        systemFeedbackService.saveSystemFeedback(systemFeedbackSaveDTO, getUserId());

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        systemFeedbackService.removeByIds(Arrays.asList(ids));

        return success();
    }

    @ApiOperation(value = "查询当前用户对应的上级反馈领导", notes = "查询当前用户对应的上级反馈领导")
    @GetMapping("/leader")
    @SysLog("查询当前用户对应的上级领导")
    public R<List<UserLeaderVo>> getLeader(){
        List<UserLeaderVo> userLeaderVoList = systemFeedbackService.getLeader(getUserId());
        return success(userLeaderVoList);
    }

}
