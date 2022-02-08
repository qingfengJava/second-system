package com.qingfeng.generaldao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 清风学Java
 * @date 2021/11/9
 * @apiNote
 */
public interface GeneralDao<T> extends Mapper<T>, MySqlMapper<T> {
}
