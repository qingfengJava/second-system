package com.qingfeng.cms.domain.club.dto;

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
 * 社团活动得分情况
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
@ApiModel(value = "ClubScoreModuleEntity", description = "社团活动得分情况")
public class ClubScoreModuleSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "社团活动表id")
	@NotNull(message = "社团活动Id不能为空")
	private Long activeApplyId;

	@ApiModelProperty(value = "得分")
	@NotNull(message = "得分不能为空")
	private BigDecimal score;

	@ApiModelProperty(value = "学期-学年")
	@NotBlank(message = "学年学期不能为空")
	private String schoolYear;
}
