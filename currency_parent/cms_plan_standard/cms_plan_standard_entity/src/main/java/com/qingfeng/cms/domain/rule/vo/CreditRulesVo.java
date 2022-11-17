package com.qingfeng.cms.domain.rule.vo;

import com.qingfeng.cms.domain.rule.enums.CreditRulesScoreGradeEnum;
import com.qingfeng.cms.domain.rule.enums.RuleCheckEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 加分（学分）细则表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CreditRulesVo",description = "学分列表模块实体")
public class CreditRulesVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "项目等级表id，外键")
	private Long levelId;

	@ApiModelProperty(value = "分数")
	private Double score;

	@ApiModelProperty(value = "分数等级划分，默认无等级（院级、校级、市级、省级、省（市）级、国家级）")
	private CreditRulesScoreGradeEnum scoreGrade;

	@ApiModelProperty(value = "分数条件字段，没有就是无")
	private String conditions;

	@ApiModelProperty(value = "是否通过审核，只有院级一下才需要审核，默认通过")
	private RuleCheckEnum isCheck;

	@ApiModelProperty(value = "审核的详情，没有就是无")
	private String checkDetail;

}
