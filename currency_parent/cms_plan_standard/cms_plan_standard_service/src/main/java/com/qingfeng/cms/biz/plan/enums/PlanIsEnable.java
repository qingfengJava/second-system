package com.qingfeng.cms.biz.plan.enums;

/**
 * 方案启用状态枚举
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/11
 */
public enum PlanIsEnable {

    ENABLE_NOT(0),
    ENABLE_TURE(1);

    private Integer enable;

    PlanIsEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
