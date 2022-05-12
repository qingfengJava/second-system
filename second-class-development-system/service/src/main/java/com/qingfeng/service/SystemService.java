package com.qingfeng.service;

import com.qingfeng.vo.ResultVO;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/12
 */
public interface SystemService {

    /**
     * 根据年份获取系统学年
     * @param schoolYear
     * @return
     */
    ResultVO getSchoolYear(String schoolYear);
}
