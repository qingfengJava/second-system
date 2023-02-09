package com.qingfeng.cms.domain.organize.dto;

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
 * 社团组织图片信息
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
@ApiModel(value = "OrganizeImgSaveDTO", description = "社团组织图片信息保存实体")
public class OrganizeImgSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "外键，社团部门Id")
	@NotNull(message = "社团部门Id不能为空")
	private Long organizeId;

	@ApiModelProperty(value = "轮播图路径")
	@NotBlank(message = "轮播图路径不能为空")
	private String imgUrl;
}
