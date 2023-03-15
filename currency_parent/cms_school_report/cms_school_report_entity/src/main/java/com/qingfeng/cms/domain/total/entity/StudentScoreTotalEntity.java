package com.qingfeng.cms.domain.total.entity;

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
 * 用户总得分情况
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
@ApiModel(value = "StudentScoreTotalEntity", description = "用户总得分情况")
@TableName("sr_student_score_total")
public class StudentScoreTotalEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "总得分")
	private BigDecimal score;

	@ApiModelProperty(value = "应得学分")
	private BigDecimal creditsScore;

	@Builder
	public StudentScoreTotalEntity(Long id, LocalDateTime createTime, Long createUser,
								   LocalDateTime updateTime, Long updateUser, Long userId,
								   BigDecimal score, BigDecimal creditsScore) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.score = score;
		this.creditsScore = creditsScore;
	}
}
