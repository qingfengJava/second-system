package com.qingfeng.cms.domain.item.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 方案模块得分情况
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
@ApiModel(value = "ItemAchievementModuleEntity", description = "方案模块得分情况")
@TableName("sr_item_achievement_module")
public class ItemAchievementModuleEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户Id")
	private Long userId;

	@ApiModelProperty(value = "项目加分申请表Id")
	private Long bonusScoreApplyId;

	@ApiModelProperty(value = "模块Id")
	private Long moduleId;

	@ApiModelProperty(value = "模块编码")
	private String moduleCode;

	@ApiModelProperty(value = "项目Id")
	private Long projectId;

	@ApiModelProperty(value = "等级Id")
	private Long levelId;

	@ApiModelProperty(value = "加分（学分）细则id")
	private Long creditRulesId;

	@ApiModelProperty(value = "得分")
	private BigDecimal score;

	@ApiModelProperty(value = "学期-学年")
	private String schoolYear;

	@Builder
	public ItemAchievementModuleEntity(Long id, LocalDateTime createTime, Long createUser,
									   LocalDateTime updateTime, Long updateUser, Long userId,
									   Long bonusScoreApplyId, Long moduleId, String moduleCode,
									   Long projectId, Long levelId, Long creditRulesId, BigDecimal score,
									   String schoolYear) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.bonusScoreApplyId = bonusScoreApplyId;
		this.moduleId = moduleId;
		this.moduleCode = moduleCode;
		this.projectId = projectId;
		this.levelId = levelId;
		this.creditRulesId = creditRulesId;
		this.score = score;
		this.schoolYear = schoolYear;
	}
}
