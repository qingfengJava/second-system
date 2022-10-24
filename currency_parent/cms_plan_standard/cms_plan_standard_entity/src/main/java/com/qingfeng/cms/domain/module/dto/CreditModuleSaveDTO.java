package com.qingfeng.cms.domain.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 学分认定模块表
 * 
 * @author qingfeng
 * @email ${email}
 * @date 2022-10-08 19:44:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CreditModuleDTO",description = "学分认定模块实体")
public class CreditModuleSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "方案Id 不能为空")
	@ApiModelProperty(value = "外键  方案id")
	private Long planId;

	@NotEmpty(message = "模块名不能为空")
	@ApiModelProperty(value = "模块名")
	private String moduleName;

	@NotEmpty(message = "模块内容不能为空")
	@ApiModelProperty(value = "模块的内容")
	private String moduleContent;

	@NotNull(message = "模块修读的学分不能为空")
	@ApiModelProperty(value = "该模块最少修读的学分（各模块最低分相加不能超过总学分）")
	private Integer minScore;

	@ApiModelProperty(value = "年份（冗余字段，方便后面查询）")
	private Integer year;

	@ApiModelProperty(value = "年级（冗余字段，方便后面进行查询）")
	private String grade;

}
