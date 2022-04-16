package com.qingfeng.dao;

import com.qingfeng.entity.Score;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 学分表的持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface ScoreMapper extends GeneralDao<Score> {
}