package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.entity.auth.Menu;

import java.util.List;

/**
 * 业务接口
 * 菜单
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据ID删除
     *
     * @param ids
     */
    boolean removeByIds(List<Long> ids);

    /**
     * 查询用户可用菜单
     *
     * @param group
     * @param userId
     * @return
     */
    List<Menu> findVisibleMenu(String group, Long userId);
}