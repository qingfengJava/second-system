package com.qingfeng.dao;

import com.qingfeng.entity.Check;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * @author 清风学Java
 */
@Repository
public interface CheckMapper extends GeneralDao<Check> {

    /**
     * 根据申请表主键Id查询终审表信息
     * @param applyId
     * @return
     */
    Check selectByApplyId(Integer applyId);
}