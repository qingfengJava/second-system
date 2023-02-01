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

    REPETITION_OF_CLASSMATE_ACTIVITIES("非法用户！");

    private String msg;
}
