package com.qingfeng.cms.domain.dict.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(value = "DictVo", description = "组织架构表   数据字典实体")
public class DictVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "父级id, 默认是0")
	private Long parentId;

	@ApiModelProperty(value = "数据字典名称")
	private String dictName;

	@ApiModelProperty(value = "数据字典对应的值，偶尔有用")
	private String dictValue;

	@ApiModelProperty(value = "数据字典对应的编码，用于分类")
	private String dictCode;

	@ApiModelProperty(value = "子集")
	private List<DictVo> children;
}
