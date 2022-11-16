package com.qingfeng.cms.domain.level.dto;

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
 * 项目等级表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LevelSaveDTO",description = "项目等级模块实体")
public class LevelSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "项目Id不能为空")
	@ApiModelProperty(value = "关联的项目id")
	private Long projectId;

	@NotEmpty(message = "等级的名字不能为空")
	@ApiModelProperty(value = "等级的名字或者是具体内容，没有就写无")
	private String levelContent;
}
