package com.qingfeng.mapper;

import com.qingfeng.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户的持久层接口
 *
 * @author 清风学Java
 * @date 2021/10/27
 * @apiNote
 */
@Repository
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param username 用户登录时的用户名
     * @return 用户对象
     */
    User findByUserName(String username);

    /**
     * 根据用户名修改密码
     * @param username 用户名
     * @param newPassword 要修改的密码
     */
    void updatePassword(String username, String newPassword);
}
