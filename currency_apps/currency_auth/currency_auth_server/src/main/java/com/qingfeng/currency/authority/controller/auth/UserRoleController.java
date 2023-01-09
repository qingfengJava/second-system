package com.qingfeng.currency.authority.controller.auth;

import com.qingfeng.currency.authority.biz.service.auth.RoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserRoleService;
import com.qingfeng.currency.authority.entity.auth.Role;
import com.qingfeng.currency.authority.entity.auth.UserRole;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user_role")
@Api(value = "用户角色信息", tags = "用户角色信息")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "根据用户Id查询角色Id及编码", notes = "根据用户Id查询角色Id及编码")
    @GetMapping("/findByUserI/{userId}")
    @SysLog("根据用户Id查询角色Id及编码")
    public R<UserRoleVo> findRoleIdByUserId(@ApiParam(value = "用户Id")
                                      @PathVariable("userId") Long userId) {

        UserRole userRole = userRoleService.getOne(Wraps.lbQ(new UserRole())
                .eq(UserRole::getUserId, userId));
        Role role = roleService.getById(userRole.getRoleId());

        return success(UserRoleVo.builder()
                .roleId(role.getId())
                .code(role.getCode())
                .build());
    }
}
