package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.dto.auth.UserUpdatePasswordDTO;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;

import java.util.List;

/**
 * 业务接口账号
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface UserService extends IService<User> {

    /**
     * 根据角色id 和 账号或名称 查询角色关联的用户
     * 注意，该接口只返回 id，账号，姓名，手机，性别
     *
     * @param roleId  角色id
     * @param keyword 账号或名称
     */
    List<User> findUserByRoleId(Long roleId, String keyword);

    /**
     * 修改输错密码的次数
     * @param id
     */
    void updatePasswordErrorNumById(Long id);

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    User getByAccount(String account);

    /**
     * 修改用户最后一次登录 时间
     * @param account
     */
    void updateLoginTime(String account);

    /**
     * 保存
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 重置密码
     * @param ids
     * @return
     */
    boolean reset(List<Long> ids);

    /**
     * 修改
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean remove(List<Long> ids);

    /**
     * 分页
     * @param page
     * @param wrapper
     * @return
     */
    IPage<User> findPage(IPage<User> page, LbqWrapper<User> wrapper);

    /**
     * 修改密码
     * @param data
     * @return
     */
    Boolean updatePassword(UserUpdatePasswordDTO data);

    /**
     * 重置密码错误次数
     * @param id
     * @return
     */
    int resetPassErrorNum(Long id);
}
