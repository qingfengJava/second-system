package com.qingfeng.dao;

import com.qingfeng.entity.Apply;
import com.qingfeng.vo.ApplyVo;
import com.qingfeng.generaldao.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 活动申请持久层
 *
 * @author 清风学Java
 */
@Repository
public interface ApplyMapper extends GeneralDao<Apply> {

    /**
     * 根据活动报名表主键Id，查询对应的活动信息
     * @param applyId
     * @return
     */
    Apply queryApplyById(Integer applyId);

    /**
     * 分页查询新活动
     * @param uid
     * @param start
     * @param limit
     * @return
     */
    List<Apply> queryApply(@Param("uid") Integer uid,
                           @Param("start") int start,
                           @Param("limit") int limit);

    /**
     * 查询新活动总记录数
     * @param uid
     * @param start
     * @param limit
     * @return
     */
    int selectApplyCount(@Param("uid") Integer uid,
                         @Param("start") int start,
                         @Param("limit") int limit);

    /**
     * 根据申请活动的Id查询对应的申请活动的详情信息
     * @param applyId
     * @return
     */
    ApplyVo selectApplyById(Integer applyId);

    /**
     * 根据申请表主键id修改申请表的审核结果
     * @param applyId
     * @param isAgree
     * @return
     */
    int updateByApplyId(@Param("applyId") Integer applyId,
                        @Param("isAgree") Integer isAgree);

    /**
     * 根据申请表主键Id删除活动申请信息
     * @param applyId
     * @return
     */
    int deleteByApplyId(Integer applyId);
}