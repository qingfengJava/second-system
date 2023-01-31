package com.qingfeng.cms.domain.sign.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.sign.enums.EvaluationStatusEnum;
import com.qingfeng.cms.domain.sign.enums.SignStatusEnum;
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
 * 活动报名表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveSignEntity", description = "活动报名表")
@TableName("ca_active_sign")
public class ActiveSignEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户Id")
	private Long userId;

	@ApiModelProperty(value = "活动申请表Id")
	private Long applyId;

	@ApiModelProperty(value = "学号")
	private String studentNum;

	@ApiModelProperty(value = "学生姓名")
	private String studentName;

	@ApiModelProperty(value = "学院，枚举值  根据数据字典处理")
	private String studentCollege;

	@ApiModelProperty(value = "专业，枚举值  根据数据字典值处理")
	private String studentMajor;

	@ApiModelProperty(value = "性别  看是否使用枚举值")
	private String studentSex;

	@ApiModelProperty(value = "联系电话")
	private String studentTel;

	@ApiModelProperty(value = "QQ号")
	private String studentQq;

	@ApiModelProperty(value = "签到状态   枚举值（待签到、已签到、超时废弃）")
	private SignStatusEnum signStatus;

	@ApiModelProperty(value = "活动评价状态 （待评价，已评价，超时废弃）")
	private EvaluationStatusEnum evaluationStatus;

	@ApiModelProperty(value = "活动评价值，采用等级")
	private Integer evaluationValue;

	@ApiModelProperty(value = "活动评价内容")
	private String evaluationContent;

	@Builder
	public ActiveSignEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
							Long updateUser, Long userId, Long applyId, String studentNum, String studentName,
							String studentCollege, String studentMajor, String studentSex, String studentTel,
							String studentQq, SignStatusEnum signStatus, EvaluationStatusEnum evaluationStatus,
							Integer evaluationValue, String evaluationContent) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.applyId = applyId;
		this.studentNum = studentNum;
		this.studentName = studentName;
		this.studentCollege = studentCollege;
		this.studentMajor = studentMajor;
		this.studentSex = studentSex;
		this.studentTel = studentTel;
		this.studentQq = studentQq;
		this.signStatus = signStatus;
		this.evaluationStatus = evaluationStatus;
		this.evaluationValue = evaluationValue;
		this.evaluationContent = evaluationContent;
	}
}
