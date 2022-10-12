package com.qingfeng.cms.domain.plan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 方案设定表（是否是修读标准，本科标准，专科标准）
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PlanUpdateDTO", description = "方案设定实体")
@TableName("crrm_plan")
public class PlanEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "方案名")
	private String planName;

	@ApiModelProperty(value = "总共需要修读的学分")
	private Integer totalScore;

	@ApiModelProperty(value = "年份（按年设计，每一年的标准不一样）")
	private Integer year;

	@ApiModelProperty(value = "年级")
	private String grade;

	@ApiModelProperty(value = "应用对象 （1：本科   2：专科）")
	private Integer applicationObject;

	@ApiModelProperty(value = "是否启用改方案（0：未启用   1：启用）")
	private Integer isEnable;

	@ApiModelProperty(value = "方案说明")
	private String planContent;

	@Builder
	public PlanEntity(Long id, LocalDateTime createTime, Long createUser,
					  LocalDateTime updateTime, Long updateUser, String planName,
					  Integer totalScore, Integer year, String grade, Integer applicationObject,
					  Integer isEnable) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.planName = planName;
		this.totalScore = totalScore;
		this.year = year;
		this.grade = grade;
		this.applicationObject = applicationObject;
		this.isEnable = isEnable;
	}
}
