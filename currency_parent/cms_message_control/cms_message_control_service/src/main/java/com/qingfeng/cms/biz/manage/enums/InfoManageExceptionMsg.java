package com.qingfeng.cms.biz.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/3
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum InfoManageExceptionMsg {

    WRONG_DATE_RANGE("日期范围已存在任务"),
    DATE_ERROR("起始日期必须大于当天日期");

    private String desc;
}
