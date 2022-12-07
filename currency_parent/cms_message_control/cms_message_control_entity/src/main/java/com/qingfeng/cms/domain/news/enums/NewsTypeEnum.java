package com.qingfeng.cms.domain.news.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
@AllArgsConstructor
@Getter
public enum NewsTypeEnum {

    MAILBOX("邮箱"),
    SHORT_MESSAGE("短信");

    private String code;
}
