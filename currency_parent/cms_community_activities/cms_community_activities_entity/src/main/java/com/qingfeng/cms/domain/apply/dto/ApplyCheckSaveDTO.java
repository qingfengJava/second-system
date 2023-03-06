package com.qingfeng.cms.domain.apply.dto;

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
 * 活动审核表
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
@ApiModel(value = "ApplyCheckSaveDTO", description = "活动审核提交")
public class ApplyCheckSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动申请表Id")
	@NotNull(message = "活动Id不能为空")
	private Long applyId;

	@ApiModelProperty(value = "审核资料(活动中的材料，比如活动照片等)")
	@NotBlank(message = "审核资料不能为空")
	private String activeCheckData;

}
