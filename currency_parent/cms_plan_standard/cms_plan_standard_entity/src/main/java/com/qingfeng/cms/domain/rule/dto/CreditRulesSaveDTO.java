package com.qingfeng.cms.domain.rule.dto;

import com.qingfeng.cms.domain.rule.enums.CreditRulesScoreGradeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 加分（学分）细则表
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
@ApiModel(value = "CreditRulesSaveDTO", description = "学分细则保存实体")
public class CreditRulesSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "等级Id不能为空")
	@ApiModelProperty(value = "项目等级表id，外键")
	private Long levelId;

	@NotNull(message = "学分不能为空")
	@ApiModelProperty(value = "分数")
	private Double score;

	@ApiModelProperty(value = "分数等级划分，默认无等级（院级、校级、市级、省级、省（市）级、国家级）")
	private CreditRulesScoreGradeEnum scoreGrade;

	@ApiModelProperty(value = "分数条件字段，没有就是无")
	private String condition;
}
