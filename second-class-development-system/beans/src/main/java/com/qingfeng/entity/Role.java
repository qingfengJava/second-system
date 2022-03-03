package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_role")
public class Role {
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

}