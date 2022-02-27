package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 社团组织实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organize {
    /**
     * 社团组织部表id 
     */
    @Id
    @Column(name = "organize_id")
    private Integer organizeId;

    /**
     * 社团用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 社团名称
     */
    @Column(name = "organize_name")
    private String organizeName;

    /**
     * 社团所属部分
     */
    @Column(name = "organize_department")
    private String organizeDepartment;

    /**
     * 社团介绍
     */
    @Column(name = "organize_inroduce")
    private String organizeInroduce;

    /**
     * 社团成立时间
     */
    @Column(name = "birth_time")
    private Date birthTime;

    /**
     * 是否删除（0：未删除   1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}