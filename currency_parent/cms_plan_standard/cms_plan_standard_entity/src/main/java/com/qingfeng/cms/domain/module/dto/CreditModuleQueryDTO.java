package com.qingfeng.cms.domain.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 学分认定模块表
 * 
 * @author qingfeng
 * @email ${email}
 * @date 2022-10-08 19:44:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CreditModuleQueryDTO",description = "学分认定模块实体")
public class CreditModuleQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模块名")
	private String moduleName;

	@ApiModelProperty(value = "年份（冗余字段，方便后面查询）")
	private Integer year;

	@ApiModelProperty(value = "年级（冗余字段，方便后面进行查询）")
	private String grade;

}
