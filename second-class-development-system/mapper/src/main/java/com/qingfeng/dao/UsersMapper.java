package com.qingfeng.dao;

import com.qingfeng.entity.Users;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 用户的持久层
 *
 * @author 清风学Java
 */
@Repository
public interface UsersMapper extends GeneralDao<Users> {
}