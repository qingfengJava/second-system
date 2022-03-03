package com.qingfeng.dao;

import com.qingfeng.entity.TeacherInfo;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 校领导及老师持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface TeacherInfoMapper extends GeneralDao<TeacherInfo> {
}