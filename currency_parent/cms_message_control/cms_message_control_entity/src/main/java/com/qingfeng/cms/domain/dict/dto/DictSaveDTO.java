package com.qingfeng.cms.domain.dict.dto;

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
 * 组织架构表   数据字典
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictSaveDTO", description = "组织架构表   数据字典保存实体")
public class DictSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "父级Id不能为空")
	@ApiModelProperty(value = "父级id, 默认是0")
	private Long parentId;

	@NotEmpty(message = "数据字典名称不能为空")
	@ApiModelProperty(value = "数据字典名称")
	private String dictName;

	@ApiModelProperty(value = "数据字典对应的值，偶尔有用")
	private String dictValue;

	@ApiModelProperty(value = "数据字典对应的编码，用于分类")
	private String dictCode;
}
