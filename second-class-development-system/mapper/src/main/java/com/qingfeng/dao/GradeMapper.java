package com.qingfeng.dao;

import com.qingfeng.entity.Grade;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 社团评级持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface GradeMapper extends GeneralDao<Grade> {
}