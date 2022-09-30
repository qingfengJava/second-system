package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.dto.auth.RoleAuthoritySaveDTO;
import com.qingfeng.currency.authority.dto.auth.UserRoleSaveDTO;
import com.qingfeng.currency.authority.entity.auth.RoleAuthority;

/**
 * 业务接口
 * 角色的资源
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface RoleAuthorityService extends IService<RoleAuthority> {

    /**
     * 给用户分配角色
     *
     * @param userRole
     */
    boolean saveUserRole(UserRoleSaveDTO userRole);

    /**
     * 给角色重新分配 权限（资源/菜单）
     *
     * @param roleAuthoritySaveDTO
     */
    boolean saveRoleAuthority(RoleAuthoritySaveDTO roleAuthoritySaveDTO);
}
