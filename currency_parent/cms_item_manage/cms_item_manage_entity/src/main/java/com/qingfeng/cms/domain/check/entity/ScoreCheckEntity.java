package com.qingfeng.cms.domain.check.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
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
 * 加分审核表
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
@ApiModel(description = "加分审核表")
@TableName("im_score_check")
public class ScoreCheckEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "加分申请表id")
	private Long scoreApplyId;

	@ApiModelProperty(value = "班级审核状态")
	private CheckStatusEnums classStatus;

	@ApiModelProperty(value = "班级反馈意见")
	private String classFeedback;

	@ApiModelProperty(value = "学院审核状态")
	private CheckStatusEnums collegeStatus;

	@ApiModelProperty(value = "学院反馈意见")
	private String collegeFeedback;

	@ApiModelProperty(value = "学生处审核状态")
	private CheckStatusEnums studentOfficeStatus;

	@ApiModelProperty(value = "学生处反馈意见")
	private String studentOfficeFeedback;

	@Builder
	public ScoreCheckEntity(Long id, LocalDateTime createTime, Long createUser,
							LocalDateTime updateTime, Long updateUser, Long scoreApplyId,
							CheckStatusEnums classStatus, String classFeedback,
							CheckStatusEnums collegeStatus, String collegeFeedback,
							CheckStatusEnums studentOfficeStatus, String studentOfficeFeedback) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.scoreApplyId = scoreApplyId;
		this.classStatus = classStatus;
		this.classFeedback = classFeedback;
		this.collegeStatus = collegeStatus;
		this.collegeFeedback = collegeFeedback;
		this.studentOfficeStatus = studentOfficeStatus;
		this.studentOfficeFeedback = studentOfficeFeedback;
	}
}
