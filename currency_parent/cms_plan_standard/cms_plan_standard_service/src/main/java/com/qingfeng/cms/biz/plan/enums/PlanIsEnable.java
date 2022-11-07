package com.qingfeng.cms.biz.plan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 方案启用状态枚举
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/11
 */
@AllArgsConstructor
@Getter
public enum PlanIsEnable {

    ENABLE_NOT(0),
    ENABLE_TURE(1);

    private Integer enable;
}
