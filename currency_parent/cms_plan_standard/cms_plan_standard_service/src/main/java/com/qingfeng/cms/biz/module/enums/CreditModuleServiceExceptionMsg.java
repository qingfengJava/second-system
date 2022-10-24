package com.qingfeng.cms.biz.module.enums;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
public enum CreditModuleServiceExceptionMsg {

    IS_EXISTENCE("学分认定模块已存在，请修改在保存！！!");

    private String msg;

    CreditModuleServiceExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
