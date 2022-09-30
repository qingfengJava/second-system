package com.qingfeng.currency.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.dto.auth.ResourceQueryDTO;
import com.qingfeng.currency.authority.entity.auth.Resource;

import java.util.List;

/**
 * 业务接口 资源
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 根据用户Id查询用户拥有的权限
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);

    /**
     * 根据菜单id删除资源
     * @param menuIds
     */
    void removeByMenuId(List<Long> menuIds);

    /**
     * 根据资源id 查询菜单id
     * @param resourceIdList
     * @return
     */
    List<Long> findMenuIdByResourceId(List<Long> resourceIdList);
}
