package com.qingfeng.cms.domain.apply.dto;

import com.qingfeng.cms.domain.apply.enums.ApplyCheckStatusEnum;
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
@ApiModel(value = "ApplyCheckDTO", description = "活动审核提交")
public class ApplyCheckDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动审核表主键Id")
	@NotNull(message = "主键Id不能为空")
	private Long id;

	@ApiModelProperty(value = "审核状态  枚举（待审核、已通过、未通过、超时废弃）（审核时间为活动结束后的一周以内，超时就废弃）")
	@NotNull(message = "审核状态不能为空")
	private ApplyCheckStatusEnum checkStatus;

	@ApiModelProperty(value = "审核建议")
	@NotBlank(message = "审核建议不能为空")
	private String checkContent;

}
