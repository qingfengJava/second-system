package com.qingfeng.currency.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.currency.authority.entity.auth.UserRole;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
