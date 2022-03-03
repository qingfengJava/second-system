package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户权限实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_permisions")
public class Permisions {
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

}