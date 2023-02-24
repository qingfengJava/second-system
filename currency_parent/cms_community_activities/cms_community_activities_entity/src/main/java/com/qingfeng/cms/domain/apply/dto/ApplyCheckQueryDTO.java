package com.qingfeng.cms.domain.apply.dto;

import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 活动申请查询
 * 
 * @author qingfeng
 * @date 2022-10-08 19:44:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ApplyCheckQueryDTO",description = "活动申请查询条件实体")
public class ApplyCheckQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动名称")
	private String activeName;

	@ApiModelProperty(value = "年份")
	private Integer year;

	@ApiModelProperty(value = "活动级别")
	private ActiveLevelEnum activeLevel;

	@ApiModelProperty(value = "活动申请状态")
	private AgreeStatusEnum agreeStatusEnum;

	@ApiModelProperty(value = "页码")
	@NotNull(message = "页码不能为空")
	private Integer pageNo;

	@ApiModelProperty(value = "每页条目数")
	@NotNull(message = "每页条目数不能为空")
	private Integer pageSize;
}
