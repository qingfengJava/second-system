package com.qingfeng.cms.biz.plan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
@Getter
@AllArgsConstructor
public enum PlanExceptionMsg {

    IS_EXISTENCE("保存失败，同一类型只能存在一个启用方案，请修改在保存！！!"),
    IS_RELATED("方案有关联的模块，不能取消启用");

    private String msg;
}
