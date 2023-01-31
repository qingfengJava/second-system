package com.qingfeng.cms.domain.apply.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.apply.enums.ApplyCheckStatusEnum;
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
 * 活动审核表
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
@ApiModel(value = "ApplyCheckEntity", description = "活动审核表")
@TableName("ca_apply_check")
public class ApplyCheckEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "提交审核的用户Id")
	private Long applyUserId;

	@ApiModelProperty(value = "活动申请表Id")
	private Long applyId;

	@ApiModelProperty(value = "审核人姓名（从审核的用户里面去获取）")
	private String checkUsername;

	@ApiModelProperty(value = "审核资料(活动中的材料，比如活动照片等)")
	private String activeCheckData;

	@ApiModelProperty(value = "审核状态  枚举（待审核、已通过、未通过、超时废弃）（审核时间为活动结束后的一周以内，超时就废弃）")
	private ApplyCheckStatusEnum checkStatus;

	@Builder
	public ApplyCheckEntity(Long id, LocalDateTime createTime, Long createUser,
							LocalDateTime updateTime, Long updateUser, Long applyUserId,
							Long applyId, String checkUsername, String activeCheckData,
							ApplyCheckStatusEnum checkStatus) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.applyUserId = applyUserId;
		this.applyId = applyId;
		this.checkUsername = checkUsername;
		this.activeCheckData = activeCheckData;
		this.checkStatus = checkStatus;
	}
}
