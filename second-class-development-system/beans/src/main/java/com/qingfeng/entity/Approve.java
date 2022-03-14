package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 精品活动审核通过表
 *
 * @author 清风学Java
 */
public class Approve {

    /**
     * 精品活动审批确定表主键Id
     */
    @Id
    @Column(name = "approve_id")
    private Integer approveId;

    /**
     * 精品活动表外键id
     */
    @Column(name = "quality_active_id")
    private Integer qualityActiveId;

    /**
     * 审核通过的用户Id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 审核人的职务
     */
    private String duty;

    /**
     * 审核单位
     */
    private String unit;

    /**
     * 审核人姓名
     */
    @Column(name = "approve_name")
    private String approveName;

    /**
     * 审核通过时间
     */
    @Column(name = "approve_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date approveTime;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
}