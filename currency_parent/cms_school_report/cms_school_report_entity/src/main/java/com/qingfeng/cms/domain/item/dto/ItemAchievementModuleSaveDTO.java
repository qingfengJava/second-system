package com.qingfeng.cms.domain.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
	private Long userId;

	@ApiModelProperty(value = "模块Id")
	private Long moduleId;

	@ApiModelProperty(value = "模块编码")
	private String moduleCode;

	@ApiModelProperty(value = "项目Id")
	private Long projectId;

	@ApiModelProperty(value = "等级Id")
	private Long levelId;

	@ApiModelProperty(value = "加分（学分）细则id")
	private Long creditRulesId;

	@ApiModelProperty(value = "得分")
	private BigDecimal score;

}
