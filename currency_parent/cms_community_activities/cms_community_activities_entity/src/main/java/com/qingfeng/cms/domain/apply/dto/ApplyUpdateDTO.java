package com.qingfeng.cms.domain.apply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveScaleEnum;
import com.qingfeng.cms.domain.apply.enums.IsBonusPointsApplyEnum;
import com.qingfeng.cms.domain.apply.enums.IsQuotaEnum;
import com.qingfeng.currency.base.entity.SuperEntity;
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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@ApiModel(value="ApplyUpdateDTO", description = "活动申请信息修改实体")
public class ApplyUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	@ApiModelProperty(value = "申请活动的用户Id，关联用户表")
	@NotNull(message = "用户Id不能为空")
	private Long applyUserId;

	@ApiModelProperty(value = "活动的名称")
	@NotBlank(message = "活动名称不能为空")
	private String activeName;

	@ApiModelProperty(value = "活动规模  小、中、大 （枚举）")
	@NotNull(message = "活动规模不能为空")
	private ActiveScaleEnum activeScale;

	@ApiModelProperty(value = "活动层级 （1：校级活动  2：院级活动  3：一般活动） 枚举")
	@NotNull(message = "活动层级不能为空")
	private ActiveLevelEnum activeLevel;

	@ApiModelProperty(value = "活动负责人")
	@NotBlank(message = "活动负责人不能为空")
	private String activePrincipal;

	@ApiModelProperty(value = "活动开始时间 ")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@NotNull(message = "活动开始时间不能为空")
	private LocalDate activeStartTime;

	@ApiModelProperty(value = "活动结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@NotNull(message = "活动结束时间不能为空")
	private LocalDate activeEndTime;

	@ApiModelProperty(value = "活动报名截止时间   必须在活动开始时间之前")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@NotNull(message = "活动报名截止时间不能为空")
	private LocalDate registrationDeadTime;

	@ApiModelProperty(value = "是否限制报名人数   枚举设置")
	private IsQuotaEnum isQuota;

	@ApiModelProperty(value = "限额人数   不限制，默认是0")
	private Integer quotaNum;

	@ApiModelProperty(value = "年份")
	@NotNull(message = "年份不能为空")
	private Integer year;

	@ApiModelProperty(value = "学年——学期")
	@NotNull(message = "学年——学期不能为空")
	private String schoolYear;

	@ApiModelProperty(value = "申请资料链接")
	@NotBlank(message = "申请资料链接不能为空")
	private String applyDataLink;

	@ApiModelProperty(value = "是否需要主办方单独进行加分文件上传  枚举设置")
	private IsBonusPointsApplyEnum isBonusPointsApply;

	@ApiModelProperty(value = "不需要上传加分文件的时候，需要设置活动分值，否则默认为0")
	private Double activeScore;

	@ApiModelProperty(value = "活动简介   申请活动时需要")
	@NotBlank(message = "活动简介不能为空")
	private String activeIntroduction;

	@ApiModelProperty(value = "活动申请时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime activeApplyTime;

}
