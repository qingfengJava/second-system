package com.qingfeng.cms.domain.apply.dto;

import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

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
@ApiModel(value = "ApplyQueryDTO",description = "活动申请查询条件实体")
public class ApplyQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动名称")
	private String activeName;

	@ApiModelProperty(value = "年份")
	private Integer year;

	@ApiModelProperty(value = "活动级别")
	private ActiveLevelEnum activeLevel;

	@ApiModelProperty(value = "活动开始时间")
	private LocalDate activeStartTime;

	@ApiModelProperty(value = "活动结束时间")
	private LocalDate activeEndTime;

	@ApiModelProperty(value = "页码")
	private Integer pageNo = 1;

	@ApiModelProperty(value = "每页条目数")
	private Integer pageSize = 10;
}
