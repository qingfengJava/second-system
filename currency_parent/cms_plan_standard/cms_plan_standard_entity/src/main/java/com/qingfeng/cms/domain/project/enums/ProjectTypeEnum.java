package com.qingfeng.cms.domain.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/6
 */
@Getter
@AllArgsConstructor
public enum ProjectTypeEnum {

    SCHOOL_ITEMS("校级项目"),
    INSTITUTE_ITEMS("院级项目"),
    GENERAL_ITEMS("一般项目");

    private String projectType;
}
