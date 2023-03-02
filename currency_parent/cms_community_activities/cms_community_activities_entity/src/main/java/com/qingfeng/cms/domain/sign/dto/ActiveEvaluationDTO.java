package com.qingfeng.cms.domain.sign.dto;

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
 * 活动报名表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveEvaluationDTO", description = "活动评价实体")
public class ActiveEvaluationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键Id")
	@NotNull(message = "主键Id不能为空")
	private Long id;

	@ApiModelProperty(value = "活动评价值，采用等级")
	@NotNull(message = "活动评价值不能为空")
	private Integer evaluationValue;

	@ApiModelProperty(value = "活动评价内容")
	@NotBlank(message = "活动评价内容不能为空")
	private String evaluationContent;
}
