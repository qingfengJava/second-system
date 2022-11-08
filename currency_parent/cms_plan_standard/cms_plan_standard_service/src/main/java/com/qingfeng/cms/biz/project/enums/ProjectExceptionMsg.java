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

    IS_EXISTS("项目已存在");

    private String msg;
}
