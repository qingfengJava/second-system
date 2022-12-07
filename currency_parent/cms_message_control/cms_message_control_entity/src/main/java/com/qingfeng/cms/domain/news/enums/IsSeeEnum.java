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

    is_viewed("已查看"),
    is_not_viewed("未查看");

    private String code;
}
