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
public class BonusScoreApplyUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	@NotNull(message = "主键Id不能为空")
	private Long id;

	@ApiModelProperty(value = "证明材料（一般为提供照片为准）zip包")
	@NotBlank(message = "证明材料不能为空")
	private String supportMaterial;

}
