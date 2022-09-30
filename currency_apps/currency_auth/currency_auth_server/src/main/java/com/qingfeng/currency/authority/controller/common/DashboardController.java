package com.qingfeng.currency.authority.controller.common;

import com.qingfeng.currency.authority.biz.service.common.LoginLogService;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.id.IdGenerate;
import com.qingfeng.currency.user.annotation.LoginUser;
import com.qingfeng.currency.user.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 前端控制器
 * 首页
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@Slf4j
@Validated
@RestController
@Api(value = "dashboard", tags = "首页")
public class DashboardController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private IdGenerate<Long> idGenerate;

    @ApiOperation(value = "查询最近10天的访问记录", notes="查询最近10天的访问记录")
    @GetMapping("/dashboard/visit")
    public R<Map<String, Object>> index(@ApiIgnore @LoginUser SysUser user) {
        Map<String, Object> data = new HashMap<>(10);
        // 获取系统访问记录
        data.put("totalVisitCount", loginLogService.findTotalVisitCount());
        data.put("todayVisitCount", loginLogService.findTodayVisitCount());
        data.put("todayIp", loginLogService.findTodayIp());
        data.put("lastTenVisitCount", loginLogService.findLastTenDaysVisitCount(null));
        data.put("lastTenUserVisitCount", loginLogService.findLastTenDaysVisitCount(user.getAccount()));
        data.put("browserCount", loginLogService.findByBrowser());
        data.put("operatingSystemCount", loginLogService.findByOperatingSystem());
        return success(data);
    }

    @ApiOperation(value="生产Id", notes="生成Id")
    @GetMapping("/common/generateId")
    public R<Long> generate() {
        return success(idGenerate.generate());
    }
}