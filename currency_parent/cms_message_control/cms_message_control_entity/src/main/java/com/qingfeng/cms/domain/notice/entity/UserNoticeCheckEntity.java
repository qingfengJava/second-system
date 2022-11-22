package com.qingfeng.cms.domain.notice.entity;

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
 * 用户公告情况表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserNoticeCheckEntity", description = "用户公告情况实体")
@TableName("mc_user_notice_check")
public class UserNoticeCheckEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公告表Id")
	private Long noticeId;

	@ApiModelProperty(value = "用户Id")
	private Long userId;

	@ApiModelProperty(value = "是否查看（未查看   已查看）(使用枚举)")
	private String isCheck;

	@Builder
	public UserNoticeCheckEntity(Long id, LocalDateTime createTime, Long createUser,
								 LocalDateTime updateTime, Long updateUser, Long noticeId,
								 Long userId, String isCheck) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.noticeId = noticeId;
		this.userId = userId;
		this.isCheck = isCheck;
	}
}
