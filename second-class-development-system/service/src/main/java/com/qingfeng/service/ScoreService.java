package com.qingfeng.service;

import com.qingfeng.vo.ResultVO;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/22
 */
public interface ScoreService {

    /**
     * 根据学生Id查询学生成绩
     * @param uid
     * @return
     */
    ResultVO queryScore(Integer uid);
}
