package com.qingfeng.cms.domain.total.vo;

import com.qingfeng.cms.domain.total.ro.ModuleRo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户总得分情况
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "StudentScoreTotalVo", description = "用户总得分情况")
public class StudentScoreTotalVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "总得分")
	private BigDecimal score;

	@ApiModelProperty(value = "应得学分")
	private BigDecimal creditsScore;

	private List<ModuleRo> moduleList;
}
