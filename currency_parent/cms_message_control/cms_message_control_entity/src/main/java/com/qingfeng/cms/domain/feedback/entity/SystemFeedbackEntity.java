package com.qingfeng.cms.domain.feedback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统反馈表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemFeedbackEntity", description = "系统反馈实体")
@TableName("mc_system_feedback")
public class SystemFeedbackEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "满意度（1~5代表不同的星级评价）")
	private Integer systemEvaluation;

	@ApiModelProperty(value = "评价者匿名姓名")
	private String anonymousName;

	@ApiModelProperty(value = "反馈对象（社团     社团联    校领导  系统管理员 ）(枚举)")
	private String feedbackType;

	@ApiModelProperty(value = "反馈对象的id")
	private Long feedbackObjectId;

	@ApiModelProperty(value = "反馈内容")
	private String content;

	@ApiModelProperty(value = "是否已回复（未回复   已回复）（枚举类型）")
	private String isReceive;

	@ApiModelProperty(value = "回复内容")
	private String receiveContent;

	@ApiModelProperty(value = "回复时间")
	private Date receiveTime;

	@ApiModelProperty(value = "是否删除（0：未删除   1：已删除）")
	private Integer isDeleted;

	@Builder
	public SystemFeedbackEntity(Long id, LocalDateTime createTime, Long createUser,
								LocalDateTime updateTime, Long updateUser, Long userId,
								Integer systemEvaluation, String anonymousName,
								String feedbackType, Long feedbackObjectId,
								String content, String isReceive, String receiveContent,
								Date receiveTime, Integer isDeleted) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.systemEvaluation = systemEvaluation;
		this.anonymousName = anonymousName;
		this.feedbackType = feedbackType;
		this.feedbackObjectId = feedbackObjectId;
		this.content = content;
		this.isReceive = isReceive;
		this.receiveContent = receiveContent;
		this.receiveTime = receiveTime;
		this.isDeleted = isDeleted;
	}
}
