package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.entity.auth.UserRole;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;

/**
 * 业务接口
 * 角色分配
 * 账号角色绑定
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户Id查询角色Id及编码
     * @param userId
     * @return
     */
    UserRoleVo findRoleIdByUserId(Long userId);
}
