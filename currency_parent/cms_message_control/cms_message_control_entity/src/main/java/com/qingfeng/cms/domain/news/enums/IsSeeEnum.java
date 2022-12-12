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
public enum IsSeeEnum {

    IS_VIEWED("已查看"),
    IS_NOT_VIEWED("未查看");

    private String code;
}
