package com.qingfeng.currency.authority.biz.service.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/23
 */
@AllArgsConstructor
@Getter
public enum UserServiceExceptionMsg {

    IMPORT_FAILD("学生信息导入异常"),
    EXPORT_FAILD("出现未知异常，导出失败！"),
    EXPORT_TEMPLATE_FAILD("学生信息模板导出异常，请重试！");

    private String msg;
}
