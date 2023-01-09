package com.qingfeng.cms.domain.feedback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.feedback.enums.IsReceiveEnum;
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

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "满意度（1~5代表不同的星级评价）")
	private Integer systemEvaluation;

	@ApiModelProperty(value = "评价者匿名姓名")
	private String anonymousName;

	@ApiModelProperty(value = "反馈对象类型")
	private String feedbackType;

	@ApiModelProperty(value = "反馈对象的id")
	private String feedbackObjectId;

	@ApiModelProperty(value = "反馈对象名")
	private String feedbackObjectName;

	@ApiModelProperty(value = "反馈内容")
	private String content;

	@ApiModelProperty(value = "是否已回复（未回复   已回复）（枚举类型）")
	private IsReceiveEnum isReceive;

	@ApiModelProperty(value = "回复内容")
	private String receiveContent;

	@ApiModelProperty(value = "回复时间")
	private Date receiveTime;


	@Builder
	public SystemFeedbackEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
								Long updateUser, Long userId, String userName, Integer systemEvaluation,
								String anonymousName, String feedbackType, String feedbackObjectId,
								String feedbackObjectName, String content, IsReceiveEnum isReceive, String receiveContent,
								Date receiveTime) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.userName = userName;
		this.systemEvaluation = systemEvaluation;
		this.anonymousName = anonymousName;
		this.feedbackType = feedbackType;
		this.feedbackObjectId = feedbackObjectId;
		this.feedbackObjectName = feedbackObjectName;
		this.content = content;
		this.isReceive = isReceive;
		this.receiveContent = receiveContent;
		this.receiveTime = receiveTime;
	}
}
