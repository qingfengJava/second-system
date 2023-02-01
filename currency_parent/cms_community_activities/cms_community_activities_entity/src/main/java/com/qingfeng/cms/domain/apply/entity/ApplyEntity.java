package com.qingfeng.cms.domain.apply.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveScaleEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveTypeEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsBonusPointsApplyEnum;
import com.qingfeng.cms.domain.apply.enums.IsQuotaEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 社团活动申请表
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
@ApiModel(value = "ApplyEntity", description = "社团活动申请表")
@TableName("ca_apply")
public class ApplyEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "申请活动的用户Id，关联用户表")
	private Long applyUserId;

	@ApiModelProperty(value = "活动的名称")
	private String activeName;

	@ApiModelProperty(value = "活动规模  小、中、大 （枚举）")
	private ActiveScaleEnum activeScale;

	@ApiModelProperty(value = "活动的类型  只能是社团活动（第四）")
	private ActiveTypeEnum activeType;

	@ApiModelProperty(value = "活动层级 （1：校级活动  2：院级活动  3：一般活动） 枚举")
	private ActiveLevelEnum activeLevel;

	@ApiModelProperty(value = "活动负责人")
	private String activePrincipal;

	@ApiModelProperty(value = "活动开始时间 ")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate activeStartTime;

	@ApiModelProperty(value = "活动结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate activeEndTime;

	@ApiModelProperty(value = "活动报名截止时间   必须在活动开始时间之前")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate registrationDeadTime;

	@ApiModelProperty(value = "是否限制报名人数   枚举设置")
	private IsQuotaEnum isQuota;

	@ApiModelProperty(value = "限额人数   不限制，默认是0")
	private Integer quotaNum;

	@ApiModelProperty(value = "年份")
	private Integer year;

	@ApiModelProperty(value = "学年——学期")
	private String schoolYear;

	@ApiModelProperty(value = "申请资料链接")
	private String applyDataLink;

	@ApiModelProperty(value = "是否需要主办方单独进行加分文件上传  枚举设置")
	private IsBonusPointsApplyEnum isBonusPointsApply;

	@ApiModelProperty(value = "加分文件连接   默认为空")
	private String bonusFile;

	@ApiModelProperty(value = "不需要上传加分文件的时候，需要设置活动分值，否则默认为0")
	private Double activeScore;

	@ApiModelProperty(value = "活动简介   申请活动时需要")
	private String activeIntroduction;

	@ApiModelProperty(value = "活动内容  在进行发布活动的时候需要")
	private String activeContent;

	@ApiModelProperty(value = "活动申请时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime activeApplyTime;

	@ApiModelProperty(value = "申请状态  枚举（申请中、已通过、未通过）")
	private AgreeStatusEnum agreeStatus;

	@ApiModelProperty(value = "活动状态  枚举（待开始，进行中，已结束，废弃）")
	private ActiveStatusEnum activeStatus;

	@ApiModelProperty(value = "是否发布")
	private IsReleaseEnum isRelease;

	@Builder
	public ApplyEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
					   Long updateUser, Long applyUserId, String activeName, ActiveScaleEnum activeScale,
					   ActiveTypeEnum activeType, ActiveLevelEnum activeLevel, String activePrincipal, LocalDate activeStartTime,
					   LocalDate activeEndTime, LocalDate registrationDeadTime, IsQuotaEnum isQuota, Integer quotaNum,
					   Integer year, String schoolYear, IsBonusPointsApplyEnum isBonusPointsApply, String bonusFile,
					   Double activeScore, String activeIntroduction, String activeContent, LocalDateTime activeApplyTime,
					   AgreeStatusEnum agreeStatus, ActiveStatusEnum activeStatus, IsReleaseEnum isRelease) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.applyUserId = applyUserId;
		this.activeName = activeName;
		this.activeScale = activeScale;
		this.activeType = activeType;
		this.activeLevel = activeLevel;
		this.activePrincipal = activePrincipal;
		this.activeStartTime = activeStartTime;
		this.activeEndTime = activeEndTime;
		this.registrationDeadTime = registrationDeadTime;
		this.isQuota = isQuota;
		this.quotaNum = quotaNum;
		this.year = year;
		this.schoolYear = schoolYear;
		this.isBonusPointsApply = isBonusPointsApply;
		this.bonusFile = bonusFile;
		this.activeScore = activeScore;
		this.activeIntroduction = activeIntroduction;
		this.activeContent = activeContent;
		this.activeApplyTime = activeApplyTime;
		this.agreeStatus = agreeStatus;
		this.activeStatus = activeStatus;
		this.isRelease = isRelease;
	}
}
