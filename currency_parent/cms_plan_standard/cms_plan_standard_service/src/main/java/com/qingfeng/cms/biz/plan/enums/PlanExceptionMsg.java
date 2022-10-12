package com.qingfeng.cms.biz.plan.enums;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/10
 */
public enum PlanExceptionMsg {

    IS_EXISTENCE("保存失败，同一类型只能存在一个启用方案，请修改在保存！！!"),
    IS_RELATED("方案有关联的模块，不能取消启用");

    private String msg;

    PlanExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
