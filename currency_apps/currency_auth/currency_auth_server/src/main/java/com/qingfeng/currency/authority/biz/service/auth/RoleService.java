package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.dto.auth.RoleSaveDTO;
import com.qingfeng.currency.authority.dto.auth.RoleUpdateDTO;
import com.qingfeng.currency.authority.entity.auth.Role;

import java.util.List;

/**
 * 业务接口
 * 角色
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据ID删除
     *
     * @param ids
     */
    boolean removeById(List<Long> ids);

    /**
     * 查询用户拥有的角色
     *
     * @param userId
     */
    List<Role> findRoleByUserId(Long userId);

    /**
     * 保存角色
     *
     * @param data
     * @param userId
     */
    void saveRole(RoleSaveDTO data, Long userId);

    /**
     * 修改
     *
     * @param role
     * @param userId
     */
    void updateRole(RoleUpdateDTO role, Long userId);

    /**
     * 根据角色编码查询用户ID
     *
     * @param codes
     */
    List<Long> findUserIdByCode(String[] codes);

    /**
     * 检测编码重复 存在返回真
     *
     * @param code
     */
    Boolean check(String code);
}
