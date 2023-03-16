package com.qingfeng.cms.domain.bonus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.bonus.enums.BonusStatusEnums;
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
 * 加分申报表（提交加分细则申请）
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "BonusScoreApplyEntity", description = "加分申报表（提交加分细则申请）")
@TableName("im_bonus_score_apply")
public class BonusScoreApplyEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "模块Id")
	private Long moduleId;

	@ApiModelProperty(value = "项目Id")
	private Long projectId;

	@ApiModelProperty(value = "等级Id")
	private Long levelId;

	@ApiModelProperty(value = "学分细则Id")
	private Long creditRulesId;

	@ApiModelProperty(value = "证明材料（一般为提供照片为准）zip包")
	private String supportMaterial;

	@ApiModelProperty(value = "加分申请的一个状态（这里的审核通过是最终审核通过）  （0：申请中   1：审核通过）")
	private BonusStatusEnums bonusStatus;

	@ApiModelProperty(value = "年份")
	private Integer year;

	@ApiModelProperty(value = "学年——学期")
	private String schoolYear;

	@Builder
	public BonusScoreApplyEntity(Long id, LocalDateTime createTime, Long createUser,
								 LocalDateTime updateTime, Long updateUser, Long userId,
								 Long moduleId, Long projectId, Long levelId,
								 Long creditRulesId, String supportMaterial, BonusStatusEnums bonusStatus) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.moduleId = moduleId;
		this.projectId = projectId;
		this.levelId = levelId;
		this.creditRulesId = creditRulesId;
		this.supportMaterial = supportMaterial;
		this.bonusStatus = bonusStatus;
	}
}