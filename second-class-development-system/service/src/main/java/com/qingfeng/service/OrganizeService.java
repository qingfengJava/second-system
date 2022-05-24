package com.qingfeng.service;

import com.qingfeng.dto.UserDto;
import com.qingfeng.entity.Organize;
import com.qingfeng.vo.ResultVO;

import java.util.List;

/**
 * 社团组织业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/5
 */
public interface OrganizeService {

    /**
     * 根据用户Id添加社团组织
     * @param uid
     * @param organize
     * @return
     */
    ResultVO addOrganizeInfo(Integer uid, Organize organize);

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
     * @param organizeName
     * @return
     */
    ResultVO queryOrganize(Integer pageNum, Integer limit,String organizeName);

    /**
     * 根据社团组织Id删除社团信息
     * @param organizeId
     * @return
     */
    ResultVO deleteById(Integer organizeId);

    /**
     * 根据主键Id批量删除社团组织信息
     * @param organizeIds
     * @return
     */
    ResultVO deleteBatch(Integer[] organizeIds);

    /**
     * 根据用户类型查询组织名称
     * @param isAdmin
     * @return
     */
    List<UserDto> queryByIsAdmin(Integer isAdmin);

    /**
     * 修改社团主图
     * @param uid
     * @param newFileName
     */
    void updateImg(Integer uid, String newFileName);

    /**
     * 添加社团轮播图信息
     * @param organizeId
     * @param newFileName
     */
    void addPhoto(Integer organizeId, String newFileName);

    /**
     * 修改社团轮播图信息
     * @param imgId
     * @param newFileName
     */
    void updateOrganizeImg(Integer imgId, String newFileName);

    /**
     * 根据id删除社团轮播图信息
     * @param imgId
     * @return
     */
    ResultVO deleteOrganizeImg(Integer imgId);
}
