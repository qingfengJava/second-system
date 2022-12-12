package com.qingfeng.cms.biz.level.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/8
 */
@Getter
@AllArgsConstructor
public enum LevelExceptionMsg {

    IS_EXISTS("项目等级已存在"),
    USER_NOT_EXITS("用户信息查询失败"),
    NEWS_SAVE_FAILED("消息通知保存失败");

    private String msg;
}
