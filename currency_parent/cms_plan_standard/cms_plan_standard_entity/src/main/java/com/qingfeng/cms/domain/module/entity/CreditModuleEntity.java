package com.qingfeng.cms.domain.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * 学分认定模块表
 * 
 * @author qingfeng
 * @email ${email}
 * @date 2022-10-08 19:44:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "学分认定模块实体")
@TableName("crrm_credit_module")
public class CreditModuleEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "外键  方案id")
	private Long planId;

	@ApiModelProperty(value = "模块名")
	@TableField(value = "module_name", condition = LIKE)
	private String moduleName;

	@ApiModelProperty(value = "模块的内容")
	private String moduleContent;

	@ApiModelProperty(value = "该模块最少修读的学分（各模块最低分相加不能超过总学分）")
	private Integer minScore;

	@ApiModelProperty(value = "年份（冗余字段，方便后面查询）")
	private Integer year;

	@ApiModelProperty(value = "年级（冗余字段，方便后面进行查询）")
	private String grade;

	@Builder
	public CreditModuleEntity(Long id, LocalDateTime createTime, Long createUser,
							  LocalDateTime updateTime, Long updateUser, Long planId,
							  String moduleName, String moduleContent, Integer minScore,
							  Integer year, String grade) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.planId = planId;
		this.moduleName = moduleName;
		this.moduleContent = moduleContent;
		this.minScore = minScore;
		this.year = year;
		this.grade = grade;
	}
}
