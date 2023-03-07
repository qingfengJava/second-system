package com.qingfeng.cms.domain.bonus.dto;

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
 * 加分申报表（提交加分细则申请）
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
@ApiModel(value = "BonusScoreApplySaveDTO", description = "加分申报实体")
public class BonusScoreApplySaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模块Id")
	@NotNull(message = "模块Id不能为空")
	private Long moduleId;

	@ApiModelProperty(value = "项目Id")
	@NotNull(message = "项目Id不能为空")
	private Long projectId;

	@ApiModelProperty(value = "等级Id")
	@NotNull(message = "等级Id不能为空")
	private Long levelId;

	@ApiModelProperty(value = "学分细则Id")
	@NotNull(message = "学分细则Id不能为空")
	private Long creditRulesId;

	@ApiModelProperty(value = "证明材料（一般为提供照片为准）zip包")
	@NotBlank(message = "证明材料不能为空")
	private String supportMaterial;

	@ApiModelProperty(value = "年份")
	@NotNull(message = "年份不能为空")
	private Integer year;

	@ApiModelProperty(value = "学年——学期")
	@NotBlank(message = "学年——学期不能为空")
	private String schoolYear;

}
