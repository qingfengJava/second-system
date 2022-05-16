package com.qingfeng.service;

import com.qingfeng.entity.TeacherInfo;
import com.qingfeng.entity.UserInfo;
import com.qingfeng.entity.Users;
import com.qingfeng.vo.ResultVO;

import java.util.List;

/**
 * 用户业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/10
 */
public interface UserService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    ResultVO checkLogin(String username, String password);

    /**
     * 添加用户
     * @param username
     * @param password
     * @param isAdmin
     * @return
     */
    ResultVO userAdd(String username, String password, int isAdmin);

    /**
     * 根据用户Id，修改用户密码
     * @param uid
     * @param password
     * @return
     */
    ResultVO updatePassword(String uid, String password);

    /**
     * 根据用户Id，完善用户信息
     * @param uid
     * @param users
     * @return
     */
    ResultVO updateMessage(Integer uid, Users users);

    /**
     * 根据用户Id查询用户信息
     * @param uid
     * @return
     */
    ResultVO checkUser(String uid);

    /**
     * 根据用户id和身份标识查询用户详情信息
     * @param uid
     * @param isAdmin
     * @return
     */
    ResultVO checkUserInfo(String uid,Integer isAdmin);

    /**
     * 根据学生用户Id添加或修改学生用户详情信息
     * @param uid
     * @param userInfo
     * @return
     */
    ResultVO updateUserInfo(Integer uid, UserInfo userInfo);

    /**
     * 根据校领导用户Id添加或修改校领导详情信息
     * @param uid
     * @param teacherInfo
     * @return
     */
    ResultVO updateTeacherInfo(Integer uid, TeacherInfo teacherInfo);

    /**
     * 根据用户身份查询用户信息
     * @param isAdmin
     * @return
     */
    List<Users> selectByUserIdentity(Integer isAdmin);

    /**
     * 分页条件查询学生用户列表信息
     * @param pageNum
     * @param limit
     * @param realName
     * @param username
     * @param isAdmin
     * @return
     */
    ResultVO findByList(int pageNum, int limit, String realName, String username,String isAdmin);

    /**
     * 用户修改头像的方法
     * @param uid
     * @param newFileName
     * @return
     */
    ResultVO updateImg(Integer uid, String newFileName);

    /**
     * 根据用户Id，删除用户信息
     * @param uid
     * @return
     */
    ResultVO deleteUserByUid(Integer uid);

    /**
     * 批量删除用户信息
     * @param uIds
     * @return
     */
    ResultVO deleteBatch(Integer[] uIds);
}
