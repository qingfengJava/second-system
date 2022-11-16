package com.qingfeng.cms.domain.rule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.rule.enums.CreditRulesScoreGradeEnum;
import com.qingfeng.cms.domain.rule.enums.RuleCheckEnum;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
@ApiModel(value = "加分（学分）细则实体")
@TableName("crrm_credit_rules")
public class CreditRulesEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目等级表id，外键")
	private Long levelId;

	@ApiModelProperty(value = "分数")
	private Double score;

	@ApiModelProperty(value = "分数等级划分，默认无等级（院级、校级、市级、省级、省（市）级、国家级）")
	private CreditRulesScoreGradeEnum scoreGrade;

	@ApiModelProperty(value = "分数条件字段，没有就是无")
	private String condition;

	@ApiModelProperty(value = "是否通过审核，只有院级一下才需要审核，默认通过")
	private RuleCheckEnum isCheck;

	@ApiModelProperty(value = "审核的详情，没有就是无")
	private String checkDetail;

	@Builder
	public CreditRulesEntity(Long id, LocalDateTime createTime, Long createUser,
							 LocalDateTime updateTime, Long updateUser,
							 Long levelId, Double score, CreditRulesScoreGradeEnum scoreGrade,
							 String condition, RuleCheckEnum isCheck, String checkDetail) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.levelId = levelId;
		this.score = score;
		this.scoreGrade = scoreGrade;
		this.condition = condition;
		this.isCheck = isCheck;
		this.checkDetail = checkDetail;
	}
}
