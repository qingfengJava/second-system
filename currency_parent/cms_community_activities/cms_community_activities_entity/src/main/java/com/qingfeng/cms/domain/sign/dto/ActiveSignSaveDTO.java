package com.qingfeng.cms.domain.sign.dto;

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
 * 活动报名表
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
@ApiModel(value = "ActiveSignSaveDTO", description = "活动报名实体")
public class ActiveSignSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户Id")
	@NotNull(message = "用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "活动申请表Id")
	@NotNull(message = "活动申请Id不能为空")
	private Long applyId;

	@ApiModelProperty(value = "学号")
	@NotBlank(message = "学号不能为空")
	private String studentNum;

	@ApiModelProperty(value = "学生姓名")
	@NotBlank(message = "学生姓名不能为空")
	private String studentName;

	@ApiModelProperty(value = "学院，枚举值  根据数据字典处理")
	@NotBlank(message = "学院值不能为空")
	private String studentCollege;

	@ApiModelProperty(value = "专业，枚举值  根据数据字典值处理")
	@NotBlank(message = "专业值不能为空")
	private String studentMajor;

	@ApiModelProperty(value = "性别")
	@NotBlank(message = "性别不能为空")
	private String studentSex;

	@ApiModelProperty(value = "联系电话")
	@NotBlank(message = "联系电话不能为空")
	private String studentTel;

	@ApiModelProperty(value = "QQ号")
	@NotBlank(message = "QQ号不能为空")
	private String studentQq;



}
