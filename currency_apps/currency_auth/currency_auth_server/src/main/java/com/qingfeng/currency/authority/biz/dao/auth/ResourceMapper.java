package com.qingfeng.currency.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.currency.authority.dto.auth.ResourceQueryDTO;
import com.qingfeng.currency.authority.entity.auth.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统资源
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据用户Id查询用户拥有的资源权限
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);

    /**
     * 根据资源Id查询菜单
     * @param resourceIdList
     * @return
     */
    List<Long> findMenuIdByResourceId(@Param("resourceIdList") List<Long> resourceIdList);

}
