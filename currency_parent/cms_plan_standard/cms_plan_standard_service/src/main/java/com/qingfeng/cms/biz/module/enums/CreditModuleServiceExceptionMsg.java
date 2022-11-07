package com.qingfeng.cms.biz.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
@AllArgsConstructor
@Getter
public enum CreditModuleServiceExceptionMsg {

    IS_EXISTENCE("学分认定模块已存在，请修改在保存！"),
    OUTOF_MIN("模块最低所修学分之和不能超过方案总学分！");

    private String msg;
}
