package com.qingfeng.currency.authority.controller.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.biz.service.common.LoginLogService;
import com.qingfeng.currency.authority.entity.common.LoginLog;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.log.annotation.SysLog;
import com.qingfeng.currency.log.util.AddressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 前端控制器
 * 登录日志
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/loginLog")
@Api(value = "LoginLog", tags = "登录日志")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询登录日志", notes = "分页查询登录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    public R<IPage<LoginLog>> page(LoginLog data) {
        IPage<LoginLog> page = this.getPage();
        // 构建值不为null的查询条件
        LbqWrapper<LoginLog> query = Wraps.<LoginLog>lbQ()
                .eq(LoginLog::getUserId, data.getUserId())
                .likeRight(LoginLog::getAccount, data.getAccount())
                .likeRight(LoginLog::getRequestIp, data.getRequestIp())
                .like(LoginLog::getLocation, data.getLocation())
                .leFooter(LoginLog::getCreateTime, this.getEndCreateTime())
                .geHeader(LoginLog::getCreateTime, this.getStartCreateTime())
                .orderByDesc(LoginLog::getId);
        this.loginLogService.page(page, query);
        return this.success(page);
    }

    @ApiOperation(value = "查询登录日志", notes = "查询登录日志")
    @GetMapping("/{id}")
    public R<LoginLog> get(@PathVariable Long id) {
        return this.success(this.loginLogService.getById(id));
    }

    @ApiOperation(value = "新增登录日志", notes = "新增登录日志不为空的字段")
    @GetMapping("/anno/login/{account}")
    public R<LoginLog> save(@NotBlank(message = "用户名不能为为空") @PathVariable String account, @RequestParam(required = false, defaultValue = "登陆成功") String description) {
        String ua = StrUtil.sub(this.request.getHeader("user-agent"), 0, 500);
        String ip = ServletUtil.getClientIP(this.request);
        String location = AddressUtil.getRegion(ip);
        // update last login time
        this.userService.updateLoginTime(account);
        LoginLog loginLog = this.loginLogService.save(account, ua, ip, location, description);
        return this.success(loginLog);
    }

    @ApiOperation(value = "删除日志", notes = "根据id物理删除登录日志")
    @DeleteMapping
    @SysLog("删除登录日志")
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        this.loginLogService.removeByIds(ids);
        return this.success(true);
    }
}