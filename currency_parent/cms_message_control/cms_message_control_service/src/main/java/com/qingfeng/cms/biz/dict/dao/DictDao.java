package com.qingfeng.cms.biz.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import org.springframework.stereotype.Repository;

/**
 * 组织架构表   数据字典
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:27
 */
@Repository
public interface DictDao extends BaseMapper<DictEntity> {
	
}
