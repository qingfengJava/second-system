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
}
