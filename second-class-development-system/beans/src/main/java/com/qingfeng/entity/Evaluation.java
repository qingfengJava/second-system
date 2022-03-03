package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Evaluation {
    /**
     * 活动评价Id，主键，无实意
     */
    @Id
    private Integer id;

    /**
     * 用户Id，关联用户表
     */
    private Integer uid;

    /**
     * 活动表id，关联活动表
     */
    @Column(name = "apply_active_id")
    private Integer applyActiveId;

    /**
     * 整体感受:1~5代表不同的星级
     */
    @Column(name = "feel_star")
    private Integer feelStar;

    /**
     * 活动满意程度： 1~5代表不同的星级
     */
    @Column(name = "satisfaction_star")
    private Integer satisfactionStar;

    /**
     * 服务态度：1~5代表不同的星级
     */
    @Column(name = "service_star")
    private Integer serviceStar;

    /**
     * 个人收获: 1~5 代表不同的星级
     */
    @Column(name = "gain_star")
    private Integer gainStar;

    /**
     * 评价内容
     */
    private String details;

    /**
     * 评价日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除： 0-未删除  1-已删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}