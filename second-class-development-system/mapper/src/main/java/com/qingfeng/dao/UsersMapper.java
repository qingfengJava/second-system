package com.qingfeng.dao;

import com.qingfeng.entity.Users;
import com.qingfeng.entity.UsersVo;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 用户的持久层
 *
 * @author 清风学Java
 */
@Repository
public interface UsersMapper extends GeneralDao<Users> {

    /**
     * 根据用户Id关联查询社团组织等相关信息
     * @param uid
     * @return
     */
    UsersVo selectByUserId(Integer uid);
}