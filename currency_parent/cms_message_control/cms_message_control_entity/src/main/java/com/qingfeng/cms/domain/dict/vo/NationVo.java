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
@ApiModel(value = "NationVo", description = "省市实体")
public class NationVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "标签")
	private String label;

	@ApiModelProperty(value = "子集")
	private List<NationVo> children;
}
