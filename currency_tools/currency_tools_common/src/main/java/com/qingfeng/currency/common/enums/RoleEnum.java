package com.qingfeng.currency.common.enums;

/**
 * 角色枚举
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/6
 */
public enum RoleEnum {

    PT_ADMIN("系统管理员"),
    STU_OFFICE_ADMIN("学生处管理员"),
    XIAOTUANWEI_LEADER("校团委组长"),
    SHETUANLIAN_LEADER("社团联联长"),
    YUAN_LEVEL_LEADER("二级学院领导"),
    STUDENT("学生"),
    SOCIAL_ORGANIZATION("社团组织");

    private String roleType;

    RoleEnum(String roleType) {
        this.roleType = roleType;
    }

    public static RoleEnum match(String val, RoleEnum def) {
        for (RoleEnum enm : RoleEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.roleType.contains(val) || val.contains(enm.roleType)) {
                return enm;
            }
        }
        return def;
    }

    public static RoleEnum get(String val) {
        return match(val, null);
    }


    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
