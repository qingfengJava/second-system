package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 社团组织轮播图实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "organize_img")
public class OrganizeImg {
    /**
     * 轮播图主键Id
     */
    @Id
    @Column(name = "img_id")
    private Integer imgId;

    /**
     * 外键，社团部门Id
     */
    @Column(name = "organize_id")
    private Integer organizeId;

    /**
     * 轮播图路径
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 状态，是否删除
     */
    private Integer status;

    /**
     * 创建的时间
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
}