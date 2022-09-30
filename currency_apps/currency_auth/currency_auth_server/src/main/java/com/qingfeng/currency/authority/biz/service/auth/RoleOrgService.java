package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.entity.auth.RoleOrg;

import java.util.List;

/**
 * 业务接口
 * 角色组织关系
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface RoleOrgService extends IService<RoleOrg> {

    /**
     * 根据角色id查询
     *
     * @param id
     */
    List<Long> listOrgByRoleId(Long id);
}
