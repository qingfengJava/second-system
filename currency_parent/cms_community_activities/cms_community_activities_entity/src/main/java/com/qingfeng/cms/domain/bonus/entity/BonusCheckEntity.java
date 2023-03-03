package com.qingfeng.cms.domain.bonus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.bonus.enums.BonusCheckStatusEnum;
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
 * 加分文件审核表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "BonusCheckEntity", description = "加分文件审核表")
@TableName("ca_bonus_check")
public class BonusCheckEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动申请表Id")
	private Long applyId;

	@ApiModelProperty(value = "审核状态  枚举（待审核、已通过、未通过）")
	private BonusCheckStatusEnum checkStatus;

	@ApiModelProperty(value = "审核建议")
	private String checkContent;

	@Builder
	public BonusCheckEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
							Long updateUser, Long applyId, BonusCheckStatusEnum checkStatus, String checkContent) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.applyId = applyId;
		this.checkStatus = checkStatus;
		this.checkContent = checkContent;
	}
}
