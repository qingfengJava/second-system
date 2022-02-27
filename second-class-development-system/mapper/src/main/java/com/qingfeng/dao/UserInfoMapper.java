package com.qingfeng.dao;

import com.qingfeng.entity.UserInfo;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 用户详情信息持久层
 *
 * @author 清风学Java
 */
@Repository
public interface UserInfoMapper extends GeneralDao<UserInfo> {
}