package com.qingfeng.cms.domain.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.manage.enums.TypeStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-12-30 17:01:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "InfoManagePageDTO", description = "方案")
public class InfoManagePageDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate startTime;

	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate endTime;

	@ApiModelProperty(value = "任务状态，待处理、处理中、已结束、已废弃")
	private TypeStatusEnum typeStatus;

}
