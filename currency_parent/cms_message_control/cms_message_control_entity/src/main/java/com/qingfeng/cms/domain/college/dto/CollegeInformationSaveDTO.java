package com.qingfeng.cms.domain.college.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 院级信息（包含班级），数据字典
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CollegeInformationSaveDTO", description = "院级信息（包含班级），数据字典实体")
public class CollegeInformationSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户Id")
	@NotNull(message = "用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "数据字典主键Id")
	@NotNull(message = "数据字典Id不能为空")
	private Long dictId;

	@ApiModelProperty(value = "组织名字   比如：攀枝花学院，数学与计算机学院")
	@NotEmpty(message = "组织名字不能为空")
	private String organizationName;

	@ApiModelProperty(value = "组织编号， 比如攀枝花学院（pzhu）")
	@NotEmpty(message = "组织编码不能为空")
	private String organizationCode;

}
