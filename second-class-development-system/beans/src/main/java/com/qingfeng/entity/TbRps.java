package com.qingfeng.entity;

import javax.persistence.*;

@Table(name = "tb_rps")
public class TbRps {
    /**
     * 角色id
     */
    @Id
    private Integer rid;

    /**
     * 权限id
     */
    private Integer pid;

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
     * 获取权限id
     *
     * @return pid - 权限id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置权限id
     *
     * @param pid 权限id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }
}