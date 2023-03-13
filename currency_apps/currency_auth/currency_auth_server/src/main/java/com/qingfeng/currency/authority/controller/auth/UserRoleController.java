package com.qingfeng.currency.authority.controller.auth;

import com.qingfeng.currency.authority.biz.service.auth.RoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserRoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.entity.auth.Role;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.auth.UserRole;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
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

import java.util.List;

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
    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据用户Id查询角色Id及编码", notes = "根据用户Id查询角色Id及编码")
    @GetMapping("/findByUserId/{userId}")
    @SysLog("根据用户Id查询角色Id及编码")
    public R<UserRoleVo> findRoleIdByUserId(@ApiParam(value = "用户Id")
                                      @PathVariable("userId") Long userId) {

        List<UserRole> list = userRoleService.list(Wraps.lbQ(new UserRole())
                .eq(UserRole::getUserId, userId));
        for (UserRole userRole : list) {
            System.out.println(userRole);
        }

        UserRole userRole = userRoleService.getOne(Wraps.lbQ(new UserRole())
                .eq(UserRole::getUserId, userId));
        Role role = roleService.getById(userRole.getRoleId());

        return success(UserRoleVo.builder()
                .roleId(role.getId())
                .code(role.getCode())
                .build());
    }

    @ApiOperation(value = "查询社团联用户信息", notes = "查询社团联用户信息")
    @GetMapping("/findroleUserInfo")
    @SysLog("查询社团联用户信息")
    public R<User> findRoleInfo(){
        //查询出社团联用户的角色Id
        Role role = roleService.getOne(Wraps.lbQ(new Role())
                .eq(Role::getCode, RoleEnum.SHETUANLIAN_LEADER)
                .eq(Role::getStatus, 1));
        UserRole userRole = userRoleService.getOne(Wraps.lbQ(new UserRole())
                .eq(UserRole::getRoleId, role.getId()));
        return success(userService.getById(userRole.getUserId()));
    }

    @ApiOperation(value = "查询班级用户信息", notes = "查询班级用户信息")
    @GetMapping("/stu/clazzInfo")
    @SysLog("查询班级用户信息")
    public R<User> findStuClazzInfo(){
        //查询出社团联用户的角色Id
        Role role = roleService.getOne(Wraps.lbQ(new Role())
                .eq(Role::getCode, RoleEnum.CLASS_GRADE)
                .eq(Role::getStatus, 1));
        UserRole userRole = userRoleService.getOne(Wraps.lbQ(new UserRole())
                .eq(UserRole::getRoleId, role.getId()));
        return success(userService.getById(userRole.getUserId()));
    }

}
