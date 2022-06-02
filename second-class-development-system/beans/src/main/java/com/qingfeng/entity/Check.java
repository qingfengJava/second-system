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

/**
 * 活动终审实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Check {
    /**
     * 审核表主键Id
     */
    @Id
    @Column(name = "checkId")
    private Integer checkid;

    /**
     * 审核组织id 
     */
    @Column(name = "check_user_id")
    private Integer checkUserId;

    /**
     * 活动id
     */
    @Column(name = "activeId")
    private Integer activeid;

    /**
     * 活动申请人的Id
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 活动举办的图片
     */
    @Column(name = "active_img")
    private String activeImg;

    /**
     * 活动总结
     */
    @Column(name = "active_summary")
    private String activeSummary;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "check_time")
    private Date checkTime;

    /**
     * 审查评价（主要用户不合格的时候）
     */
    @Column(name = "check_content")
    private String checkContent;

    /**
     * 是否通过(0: 审核不通过  1：通过)
     */
    @Column(name = "is_pass")
    private Integer isPass;

    /**
     * 是否删除（0：未删除  1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

}