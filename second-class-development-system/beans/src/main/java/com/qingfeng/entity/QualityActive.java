package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 精品活动申请快照表
 * @author 清风学Java
 */
@Table(name = "quality_active")
public class QualityActive {
    /**
     * 精品活动快照表主键Id
     */
    @Id
    @Column(name = "quality_active_id")
    private Integer qualityActiveId;

    /**
     * 活动申请表的外键Id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 活动名称（快照）
     */
    @Column(name = "active_name")
    private String activeName;

    /**
     * 社团组织名字（快照）
     */
    @Column(name = "organize_name")
    private String organizeName;

    /**
     * 主办单位（快照）
     */
    @Column(name = "host_name")
    private String hostName;

    /**
     * 活动规模（历届活动规模人数）
     */
    @Column(name = "active_num")
    private Integer activeNum;

    /**
     * 历届活动图片
     */
    private String img;

    /**
     * 活动介绍
     */
    private String introduce;

    /**
     * 活动意义
     */
    @Column(name = "active_mean")
    private String activeMean;

    /**
     * 活动预计时间
     */
    @Column(name = "active_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date activeTime;

    /**
     * 学年
     */
    @Column(name = "school_year")
    private String schoolYear;

    /**
     * 初级申请审核阶段（申请是否成功，不是认定）
     */
    @Column(name = "is_check")
    private Integer isCheck;

    /**
     * 是否确定为精品活动
     */
    @Column(name = "is_confirm")
    private Integer isConfirm;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;
}