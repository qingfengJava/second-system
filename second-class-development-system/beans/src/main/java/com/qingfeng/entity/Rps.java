package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色权限表实体类
 *
 * @author 清风学Java
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tb_rps")
public class Rps {
    /**
     * 角色id
     */
    @Id
    private Integer rid;

    /**
     * 权限id
     */
    private Integer pid;

}