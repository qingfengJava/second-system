package com.qingfeng.dao;

import com.qingfeng.entity.AuditForm;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 初级审核表的持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface AuditFormMapper extends GeneralDao<AuditForm> {

    /**
     * 根据活动申请表的id删除活动初级审核表中的信息
     * @param applyId
     * @return
     */
    int deleteByApplyId(Integer applyId);
}