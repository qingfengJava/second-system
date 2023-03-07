package com.qingfeng.cms.domain.evaluation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 项目评价在线反馈表(对已参与的项目进行评价反馈)（类似于间接评价）
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "项目评价在线反馈表(对已参与的项目进行评价反馈)（类似于间接评价）")
@TableName("im_evaluation_feedback")
public class EvaluationFeedbackEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id（学生进行评价）")
	private Long userId;

	@ApiModelProperty(value = "分申请表id，一定是加分申请成功才能进行评价的")
	private Long scoreApplyId;

	@ApiModelProperty(value = "活动评价值，采用等级")
	private Integer evaluationValue;

	@ApiModelProperty(value = "项目评价内容")
	private String evaluationContent;

	@Builder
	public EvaluationFeedbackEntity(Long id, LocalDateTime createTime, Long createUser,
									LocalDateTime updateTime, Long updateUser, Long userId,
									Long scoreApplyId, Integer evaluationValue,
									String evaluationContent) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.scoreApplyId = scoreApplyId;
		this.evaluationValue = evaluationValue;
		this.evaluationContent = evaluationContent;
	}
}
