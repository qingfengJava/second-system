package com.qingfeng.cms.biz.college.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
@AllArgsConstructor
@Getter
public enum CollegeInformationExceptionMsg {

    ILLEGAL_USER("非法用户！"),
    IMPROVE_COLLEGE_INFORMATION("请前往个人中心完善关联的二级学院信息");

    private String msg;
}
