package com.qingfeng.cms.domain.check.dto;

import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
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
import java.math.BigDecimal;

/**
 * 加分审核表
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
@ApiModel(value = "ScoreCheckSaveDTO", description = "加分审核表")
public class ScoreCheckSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "加分申请表id")
	@NotNull(message = "项目申请Id不能为空")
	private Long scoreApplyId;

	@ApiModelProperty(value = "审核状态")
	@NotNull(message = "审核状态不能为空")
	private CheckStatusEnums status;

	@ApiModelProperty(value = "反馈意见")
	@NotBlank(message = "反馈意见不能为空")
	private String feedback;

	@ApiModelProperty(value = "只有在最后一级的时候才能设置这个分数")
	private BigDecimal score;
}
