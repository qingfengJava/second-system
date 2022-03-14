package com.qingfeng.service;

import com.qingfeng.entity.Grade;
import com.qingfeng.vo.ResultVO;

/**
 * 社团评级业务层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/14
 */
public interface GradeService {

    /**
     * 添加社团评级
     * @param organizeId
     * @param grade
     * @return
     */
    ResultVO commentGrade(Grade grade);
}
