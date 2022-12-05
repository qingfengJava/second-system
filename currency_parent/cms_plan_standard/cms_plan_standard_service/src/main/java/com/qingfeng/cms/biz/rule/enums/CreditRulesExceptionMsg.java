package com.qingfeng.cms.biz.rule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/8
 */
@Getter
@AllArgsConstructor
public enum CreditRulesExceptionMsg {

    IS_EXISTS("学分细则已存在"),
    GRADE_IS_SAME("学分不能存在相同的等级");

    private String msg;
}
