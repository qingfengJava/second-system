package com.qingfeng.cms.biz.dict.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
@AllArgsConstructor
@Getter
public enum DictServiceExceptionMsg {

    IS_EXISTENCE("同级数据字典名字不能相同！"),
    IS_EXISTCODE("编码必须是唯一标识且大写"),
    EXPORT_TEMPLATE_FAILD("数据字典模板导出异常，请重试！"),
    EXPORT_FAILD("数据字典导出异常，请重试！"),
    IMPORT_FAILD("数据字典导入异常，请重试！");

    private String msg;
}
