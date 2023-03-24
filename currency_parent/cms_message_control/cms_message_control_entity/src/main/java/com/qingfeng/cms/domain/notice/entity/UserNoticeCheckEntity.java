package com.qingfeng.cms.domain.notice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户公告情况表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-24 22:28:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "用户公告情况表")
@TableName("mc_user_notice_check")
public class UserNoticeCheckEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公告表Id")
	private Long noticeId;

	@ApiModelProperty(value = "用户Id")
	private Long userId;

	@Builder
	public UserNoticeCheckEntity(Long id, LocalDateTime createTime, Long createUser,
								 LocalDateTime updateTime, Long updateUser, Long noticeId,
								 Long userId) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.noticeId = noticeId;
		this.userId = userId;
	}
}
