package com.qingfeng.cms.biz.apply.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
@AllArgsConstructor
@Getter
public enum ApplyExceptionMsg {

    REPETITION_OF_CLASSMATE_ACTIVITIES("非法用户！"),
    ACTIVITY_APPLICATION_APPROVAL_EXCEPTION("活动申请审核异常"),
    NEWS_SAVE_FAILED("消息通知保存失败！");

    private String msg;
}
