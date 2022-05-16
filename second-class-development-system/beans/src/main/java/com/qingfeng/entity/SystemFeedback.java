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
 * 系统反馈实体表
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "system_feedback")
public class SystemFeedback {
    /**
     * 系统反馈表主键Id
     */
    @Id
    @Column(name = "system_id")
    private Integer systemId;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 满意度（1~5代表不同的星级评价）
     */
    @Column(name = "system_evaluation")
    private Integer systemEvaluation;

    /**
     * 评价者匿名姓名
     */
    @Column(name = "anonymous_name")
    private String anonymousName;

    /**
     * 反馈对象（2：社团     3：社团联     4：校领导  5：系统管理员 ）
     */
    @Column(name = "feedback_type")
    private Integer feedbackType;

    /**
     * 反馈对象的id
     */
    @Column(name = "feedback_object_id")
    private Integer feedbackObjectId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否已接收（0：未接收    1：已接收）
     */
    @Column(name = "is_receive")
    private Integer isReceive;

    /**
     * 回复内容
     */
    @Column(name = "receive_content")
    private String receiveContent;

    /**
     * 回复时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "receive_time")
    private Date receiveTime;

    /**
     * 是否删除（0：未删除   1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;
}