package com.qingfeng.dao;

import com.qingfeng.entity.Organize;
import com.qingfeng.vo.OrganizeVo;
import com.qingfeng.generaldao.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据社团组织用户的主键Id查询社团组织的详情信息，含轮播图信息
     * @param uid
     * @return
     */
    OrganizeVo checkOrganizeInfo(Integer uid);
}