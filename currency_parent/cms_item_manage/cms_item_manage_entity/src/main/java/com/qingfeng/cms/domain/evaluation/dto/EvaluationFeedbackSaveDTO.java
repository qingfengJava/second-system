package com.qingfeng.cms.domain.evaluation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class EvaluationFeedbackSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分申请表id，一定是加分申请成功才能进行评价的")
	@NotNull(message = "项目申请表Id不能为空")
	private Long scoreApplyId;

	@ApiModelProperty(value = "活动评价值，采用等级")
	@NotNull(message = "活动评价值不能为空")
	private Integer evaluationValue;

	@ApiModelProperty(value = "项目评价内容")
	@NotBlank(message = "项目评价内容不能为空")
	private String evaluationContent;
}
