package com.qingfeng.entity;

import java.util.Date;
import javax.persistence.*;

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
     * 评价内容
     */
    private String details;

    /**
     * 满意程度:1~5代表不同的星级
     */
    @Column(name = "star_level")
    private Integer starLevel;

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

    /**
     * 获取活动评价Id，主键，无实意
     *
     * @return id - 活动评价Id，主键，无实意
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置活动评价Id，主键，无实意
     *
     * @param id 活动评价Id，主键，无实意
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户Id，关联用户表
     *
     * @return uid - 用户Id，关联用户表
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置用户Id，关联用户表
     *
     * @param uid 用户Id，关联用户表
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取活动表id，关联活动表
     *
     * @return apply_active_id - 活动表id，关联活动表
     */
    public Integer getApplyActiveId() {
        return applyActiveId;
    }

    /**
     * 设置活动表id，关联活动表
     *
     * @param applyActiveId 活动表id，关联活动表
     */
    public void setApplyActiveId(Integer applyActiveId) {
        this.applyActiveId = applyActiveId;
    }

    /**
     * 获取评价内容
     *
     * @return details - 评价内容
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置评价内容
     *
     * @param details 评价内容
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * 获取满意程度:1~5代表不同的星级
     *
     * @return star_level - 满意程度:1~5代表不同的星级
     */
    public Integer getStarLevel() {
        return starLevel;
    }

    /**
     * 设置满意程度:1~5代表不同的星级
     *
     * @param starLevel 满意程度:1~5代表不同的星级
     */
    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    /**
     * 获取评价日期
     *
     * @return create_time - 评价日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置评价日期
     *
     * @param createTime 评价日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取是否删除： 0-未删除  1-已删除
     *
     * @return is_deleted - 是否删除： 0-未删除  1-已删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否删除： 0-未删除  1-已删除
     *
     * @param isDeleted 是否删除： 0-未删除  1-已删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}