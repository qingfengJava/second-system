package com.qingfeng.cms.domain.feedback.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel(value = "SystemFeedbackSaveDTO", description = "系统反馈保存实体")
public class SystemFeedbackSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "满意度（1~5代表不同的星级评价）")
	@NotNull(message = "对当前系统使用的满意度，不能为空")
	private Integer systemEvaluation;

	@ApiModelProperty(value = "评价者匿名姓名")
	@NotBlank(message = "评价者匿名姓名不能为空")
	private String anonymousName;

	@ApiModelProperty(value = "反馈对象类型")
	@NotBlank(message = "反馈对象类型不能为空")
	private String feedbackType;

	@ApiModelProperty(value = "反馈内容")
	@NotBlank(message = "反馈内容不能为空")
	private String content;
}
