package com.qingfeng.dao;

import com.qingfeng.entity.Organize;
import com.qingfeng.generaldao.GeneralDao;
import com.qingfeng.vo.OrganizeVo;
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

    /**
     * 根据身份类型查询社团组织信息
     * @param isAdmin
     * @return
     */
    List<Organize> queryByIsAdmin(Integer isAdmin);

    /**
     * 查询年度待评级的社团组织信息
     * @param start
     * @param limit
     * @param schoolYear
     * @param organizeName
     * @return
     */
    List<Organize> queryOrganizeByGrade(@Param("start") int start,
                                        @Param("limit") Integer limit,
                                        @Param("schoolYear") String schoolYear,
                                        @Param("organizeName") String organizeName);

    int queryOrganizeByGradeCount(@Param("schoolYear") String schoolYear,
                                  @Param("organizeName") String organizeName);
}