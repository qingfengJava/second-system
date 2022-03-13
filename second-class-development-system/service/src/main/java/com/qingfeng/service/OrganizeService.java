package com.qingfeng.service;

import com.qingfeng.entity.Organize;
import com.qingfeng.vo.ResultVO;

/**
 * 社团组织业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/5
 */
public interface OrganizeService {

    /**
     * 根据用户Id添加或修改社团组织详情Id
     * @param uid
     * @param organize
     * @return
     */
    ResultVO updateOrganizeInfo(Integer uid, Organize organize);

    /**
     * 根据社团组织Id查询社团组织的详情信息，含轮播图
     * @param uid
     * @return
     */
    ResultVO checkOrganizeInfo(Integer uid);

    /**
     * 分页查询社团组织列表
     * @param pageNum
     * @param limit
     * @return
     */
    ResultVO queryOrganize(Integer pageNum, Integer limit);
}
