package com.qingfeng.service.impl;

import com.qingfeng.entity.User;
import com.qingfeng.mapper.UserMapper;
import com.qingfeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author 清风学Java
 * @date 2021/10/27
 * @apiNote
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户的业务层需要维护用户持久层的对象
     */
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户登录
     * @param username 用户登录时的用户名
     * @param password 用户登录时的密码
     * @return 用户对象
     */
    @Override
    public User login(String username, String password) {
        //根据控制层传过来的用户名，查询用户
        User user = userMapper.findByUserName(username);
        if (ObjectUtils.isEmpty(user) || user.getIsDelete() == 1){
            //说明用户对象不存在
            throw new RuntimeException("用户不存在！");
        }

        //用户存在，则判断密码是否正确
        if (!user.getPassword().equals(password)){
            //密码不匹配，抛出异常提示信息
            throw new RuntimeException("密码错误!");
        }

        //密码也匹配正确。返回user对象
        return user;
    }

    /**
     * 根据用户名修改密码
     * @param username 用户名
     * @param newPassword 要修改的密码
     */
    @Override
    public void updatePassword(String username, String newPassword) {
        try {
            //调用mapper层的修改密码的方法，对密码进行修改
            userMapper.updatePassword(username,newPassword);
        } catch (Exception e) {
            throw new RuntimeException("修改密码失败！");
        }
    }

    @Override
    public void updateByUid(User user) {
        //根据用户Id，修改完善用户信息
        userMapper.updateByUid(user);
    }
}
