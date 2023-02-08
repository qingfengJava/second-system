package com.qingfeng.cms.domain.organize.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.organize.enums.OrganizeLevelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 社团组织详情表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrganizeInfoSaveDTO", description = "社团组织详情信息保存")
public class OrganizeInfoSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "社团用户id")
	@NotNull(message = "社团用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "社团名称")
	@NotBlank(message = "社团名称不能为空")
	private String organizeName;

	@ApiModelProperty(value = "社团所属组织")
	@NotBlank(message = "社团所属组织不能为空")
	private String organizeDepartment;

	@ApiModelProperty(value = "社团级别（校级，院级）")
	@NotNull(message = "社团级别不能为空")
	private OrganizeLevelEnum organizeLevel;

	@ApiModelProperty(value = "社团介绍")
	@NotBlank(message = "社团介绍不能为空")
	private String organizeIntroduce;

	@ApiModelProperty(value = "社团指导老师")
	@NotBlank(message = "社团指导老师不能为空")
	private String teacherName;

	@ApiModelProperty(value = "社团介绍视频")
	private String video;

	@ApiModelProperty(value = "成立时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@NotNull(message = "社团成立时间不能为空")
	private LocalDate birthTime;
}
