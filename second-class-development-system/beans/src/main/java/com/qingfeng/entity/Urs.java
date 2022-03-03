package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色表
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_urs")
public class Urs {
    /**
     * 用户id
     */
    @Id
    private Integer uid;

    /**
     * 角色id
     */
    private Integer rid;

}