package com.qingfeng.cms.domain.dict.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/30
 */
@AllArgsConstructor
@Getter
public enum DictDepartmentEnum {

    PZHU("攀枝花学院"),
    SJ("数学与计算机学院（大数据学院）"),
    ZNZZ("智能制造学院"),
    JG("经济与管理学院");
    // TODO 学院信息待完善

    private String code;
}
