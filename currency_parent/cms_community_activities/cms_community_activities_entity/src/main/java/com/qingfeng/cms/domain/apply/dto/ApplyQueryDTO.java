package com.qingfeng.cms.domain.apply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate activeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ApiModelProperty(value = "活动结束时间")
	private LocalDate activeEndTime;

	@ApiModelProperty(value = "页码")
	@NotNull(message = "页码不能为空")
	private Integer pageNo;

	@ApiModelProperty(value = "每页条目数")
	@NotNull(message = "每页条目数不能为空")
	private Integer pageSize;
}
