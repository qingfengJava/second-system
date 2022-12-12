package com.qingfeng.cms.biz.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/8
 */
@Getter
@AllArgsConstructor
public enum ProjectExceptionMsg {

    IS_EXISTS("项目已存在"),
    NEWS_SAVE_FAILED("消息通知保存失败！"),
    USER_NOT_EXITS("用户信息查询失败");

    private String msg;
}
