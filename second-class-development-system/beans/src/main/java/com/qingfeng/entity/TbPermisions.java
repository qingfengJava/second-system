package com.qingfeng.entity;

import javax.persistence.*;

@Table(name = "tb_permisions")
public class TbPermisions {
    /**
     * 权限id
     */
    @Id
    @Column(name = "permision_id")
    private Integer permisionId;

    /**
     * 对应的操作权限
     */
    @Column(name = "permision_code")
    private String permisionCode;

    /**
     * 权限对应的操作名称
     */
    @Column(name = "permision_name")
    private String permisionName;

    /**
     * 获取权限id
     *
     * @return permision_id - 权限id
     */
    public Integer getPermisionId() {
        return permisionId;
    }

    /**
     * 设置权限id
     *
     * @param permisionId 权限id
     */
    public void setPermisionId(Integer permisionId) {
        this.permisionId = permisionId;
    }

    /**
     * 获取对应的操作权限
     *
     * @return permision_code - 对应的操作权限
     */
    public String getPermisionCode() {
        return permisionCode;
    }

    /**
     * 设置对应的操作权限
     *
     * @param permisionCode 对应的操作权限
     */
    public void setPermisionCode(String permisionCode) {
        this.permisionCode = permisionCode;
    }

    /**
     * 获取权限对应的操作名称
     *
     * @return permision_name - 权限对应的操作名称
     */
    public String getPermisionName() {
        return permisionName;
    }

    /**
     * 设置权限对应的操作名称
     *
     * @param permisionName 权限对应的操作名称
     */
    public void setPermisionName(String permisionName) {
        this.permisionName = permisionName;
    }
}