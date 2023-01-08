package com.qingfeng.cms.domain.manage.dto;

import com.qingfeng.cms.domain.manage.enums.InfoTypeEnum;
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
import java.time.LocalDate;

/**
 * 
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-12-30 17:01:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "InfoManageSaveDTO", description = "信息管理保存实体")
public class InfoManageSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "标题")
	@NotBlank(message = "标题不能为空")
	private String title;

	@ApiModelProperty(value = "维护对象类型，方便以后拓展（学生，教师）枚举处理")
	@NotNull(message = "处理对象不能为空")
	private InfoTypeEnum type;

	@ApiModelProperty(value = "维护对象的年级，对于学生是年级，对于老师来说就是老师")
	@NotNull(message = "对象的年级不能为空")
	private String typeGrade;

	@ApiModelProperty(value = "执行人")
	@NotBlank(message = "执行人不能为空")
	private String executor;

	@ApiModelProperty(value = "开始时间")
	@NotNull(message = "开始时间不能为空")
	private LocalDate startTime;

	@ApiModelProperty(value = "结束时间")
	@NotNull(message = "结束时间不能为")
	private LocalDate endTime;
}
