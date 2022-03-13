package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * 社团组织实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrganizeVo {
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 维护社团组织轮播图信息
     */
    private List<OrganizeImg> organizeImg;
}