package com.qingfeng.cms.biz.college.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import org.springframework.stereotype.Repository;

/**
 * 院级信息（包含班级），数据字典
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Repository
public interface CollegeInformationDao extends BaseMapper<CollegeInformationEntity> {
	
}
