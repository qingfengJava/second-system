package com.qingfeng.currency.authority.biz.service.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.entity.core.Org;

import java.util.List;

/**
 * 业务接口
 * 组织
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
public interface OrgService extends IService<Org> {

    /**
     * 查询指定id集合下的所有子集
     *
     * @param ids
     */
    List<Org> findChildren(List<Long> ids);

    /**
     * 批量删除以及删除其子节点
     *
     * @param ids
     */
    boolean remove(List<Long> ids);
}
