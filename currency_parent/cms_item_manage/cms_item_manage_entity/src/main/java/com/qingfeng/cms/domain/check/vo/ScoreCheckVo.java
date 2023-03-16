package com.qingfeng.cms.domain.check.vo;

import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel(value = "ScoreCheckVo", description = "加分审核表")
public class ScoreCheckVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键Id")
	private Long id;

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

	@ApiModelProperty(value = "不同阶段的审核状态，展示在页面上使用的")
	private CheckStatusEnums status;

	@ApiModelProperty(value = "不同阶段的审核一键，展示在页面上使用的")
	private String feedback;

	@ApiModelProperty(value = "是否能被当前用户处理")
	private Boolean canBeHandled;
}
