package com.qingfeng.cms.domain.notice.entity;

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

/**
 * 系统公告表
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
@ApiModel(description = "系统公告表")
@TableName("mc_notice")
public class NoticeEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "发布人（用户）id（关联发布人信息）")
	private Long userId;

	@ApiModelProperty(value = "公告标题")
	private String title;

	@ApiModelProperty(value = "公告作者")
	private String publicName;

	@ApiModelProperty(value = "公告内容")
	private String content;

	@ApiModelProperty(value = "发布人角色编码（code）")
	private String userCode;

	@ApiModelProperty(value = "阅读量")
	private Integer readNum;

	@Builder
	public NoticeEntity(Long id, LocalDateTime createTime, Long createUser,
						LocalDateTime updateTime, Long updateUser, Long userId,
						String title, String publicName, String content, String userCode,
						Integer readNum) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.title = title;
		this.publicName = publicName;
		this.content = content;
		this.userCode = userCode;
		this.readNum = readNum;
	}
}
