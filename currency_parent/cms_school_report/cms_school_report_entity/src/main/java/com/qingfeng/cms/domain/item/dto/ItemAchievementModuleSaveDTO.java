package com.qingfeng.cms.domain.item.dto;

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
 * 方案模块得分情况
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ItemAchievementModuleSaveDTO", description = "方案模块得分情况")
public class ItemAchievementModuleSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户Id")
	@NotNull(message = "用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "项目加分申请表Id")
	@NotNull(message = "项目加分申请表Id不能为空")
	private Long bonusScoreApplyId;

	@ApiModelProperty(value = "模块Id")
	@NotNull(message = "模块Id不能为空")
	private Long moduleId;

	@ApiModelProperty(value = "模块编码")
	@NotBlank(message = "模块编码不能为空")
	private String moduleCode;

	@ApiModelProperty(value = "项目Id")
	@NotNull(message = "项目id不能为空")
	private Long projectId;

	@ApiModelProperty(value = "等级Id")
	@NotNull(message = "等级id不能为空")
	private Long levelId;

	@ApiModelProperty(value = "加分（学分）细则id")
	private Long creditRulesId;

	@ApiModelProperty(value = "得分")
	@NotNull(message = "得分不能为空")
	private BigDecimal score;

	@ApiModelProperty(value = "学期-学年")
	@NotBlank(message = "学年学期不能为空")
	private String schoolYear;
}
