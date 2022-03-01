package com.qingfeng.dao;

import com.qingfeng.entity.Organize;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 社团组织持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface OrganizeMapper extends GeneralDao<Organize> {

    /**
     * 根据用户Id查询社团组织信息
     * @param uid
     * @return
     */
    Organize selectOrganizeByUserId(Integer uid);
}