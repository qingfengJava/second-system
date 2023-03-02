package com.qingfeng.cms.domain.module.dto;

import com.qingfeng.cms.domain.module.enums.CreditModuleTypeEnum;
import com.qingfeng.currency.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@ApiModel(value = "CreditModuleUpdateDTO",description = "学分认定模块实体")
public class CreditModuleUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	@NotNull(message = "方案Id 不能为空")
	@ApiModelProperty(value = "外键  方案id")
	private Long planId;

	@NotEmpty(message = "模块名不能为空")
	@ApiModelProperty(value = "模块名")
	private String moduleName;

	@ApiModelProperty(value = "模块的标识编码")
	@NotNull(message = "模块标识编码不能为空")
	private CreditModuleTypeEnum code;

	@NotEmpty(message = "模块内容不能为空")
	@ApiModelProperty(value = "模块的内容")
	private String moduleContent;

	@NotNull(message = "模块修读的学分不能为空")
	@Min(value = 2)
	@Max(value = 3)
	@ApiModelProperty(value = "该模块最少修读的学分（各模块最低分相加不能超过总学分）")
	private Integer minScore;


}
