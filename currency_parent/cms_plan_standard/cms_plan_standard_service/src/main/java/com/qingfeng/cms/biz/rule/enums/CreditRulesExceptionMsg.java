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
    GRADE_IS_SAME("学分不能存在相同的等级"),
    USER_NOT_EXITS("用户查询不存在"),
    NEWS_SAVE_FAILED("消息通知保存失败");

    private String msg;
}
