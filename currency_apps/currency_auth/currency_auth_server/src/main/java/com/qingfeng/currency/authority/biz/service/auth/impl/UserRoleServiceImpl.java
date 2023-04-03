package com.qingfeng.currency.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.authority.biz.dao.auth.UserRoleMapper;
import com.qingfeng.currency.authority.biz.service.auth.RoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserRoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.entity.auth.Role;
import com.qingfeng.currency.authority.entity.auth.UserRole;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务实现类
 * 角色分配
 * 账号角色绑定
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    /**
     * 根据用户Id查询角色Id及编码
     * @param userId
     * @return
     */
    @Override
    public UserRoleVo findRoleIdByUserId(Long userId) {
        List<UserRole> list = userRoleService.list(Wraps.lbQ(new UserRole())
                .eq(UserRole::getUserId, userId));

        UserRole userRole = userRoleService.getOne(Wraps.lbQ(new UserRole())
                .eq(UserRole::getUserId, userId));
        Role role = roleService.getById(userRole.getRoleId());
        return UserRoleVo.builder()
                .roleId(role.getId())
                .code(role.getCode())
                .build();
    }
}