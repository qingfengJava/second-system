package com.qingfeng.cms.domain.club.entity;

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
 * 社团活动得分情况
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
@ApiModel(value = "ClubScoreModuleEntity", description = "社团活动得分情况")
@TableName("sr_club_score_module")
public class ClubScoreModuleEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "社团活动表id")
	private Long activeApplyId;

	@ApiModelProperty(value = "得分")
	private BigDecimal score;

	@ApiModelProperty(value = "学期-学年")
	private String schoolYear;

	@Builder
	public ClubScoreModuleEntity(Long id, LocalDateTime createTime, Long createUser,
								 LocalDateTime updateTime, Long updateUser, Long userId,
								 Long activeApplyId, BigDecimal score, String schoolYear) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.activeApplyId = activeApplyId;
		this.score = score;
		this.schoolYear = schoolYear;
	}
}
