package com.qingfeng.entity;

import javax.persistence.*;

@Table(name = "tb_role")
public class TbRole {
    /**
     * 角色id
     */
    @Id
    private Integer rid;

    /**
     * 角色的名字
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 获取角色id
     *
     * @return rid - 角色id
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * 设置角色id
     *
     * @param rid 角色id
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * 获取角色的名字
     *
     * @return role_name - 角色的名字
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色的名字
     *
     * @param roleName 角色的名字
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}