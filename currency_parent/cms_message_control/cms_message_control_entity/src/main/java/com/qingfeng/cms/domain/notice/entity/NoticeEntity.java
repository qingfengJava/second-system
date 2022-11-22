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
 * 系统公告表
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
@ApiModel(value = "NoticeEntity", description = "系统公告实体")
@TableName("mc_notice")
public class NoticeEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "发布人（用户）id（关联发布人信息）")
	private Long userId;

	@ApiModelProperty(value = "公告主题")
	private String title;

	@ApiModelProperty(value = "发布人姓名")
	private String publicName;

	@ApiModelProperty(value = "职位")
	private String duty;

	@ApiModelProperty(value = "公告内容")
	private String content;

	@ApiModelProperty(value = "发布的对象(角色类型，枚举表示）")
	private String releaseObject;

	@ApiModelProperty(value = "阅读量")
	private Integer readNum;

	@ApiModelProperty(value = "是否需要用户对象处理(使用枚举类型)（不需要  需要处理）")
	private String isTask;

	@ApiModelProperty(value = "是否删除   0：未删除  1：已删除")
	private Integer isDeleted;

	@Builder
	public NoticeEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
						Long updateUser, Long userId, String title, String publicName, String duty,
						String content, String releaseObject, Integer readNum, String isTask, Integer isDeleted) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.title = title;
		this.publicName = publicName;
		this.duty = duty;
		this.content = content;
		this.releaseObject = releaseObject;
		this.readNum = readNum;
		this.isTask = isTask;
		this.isDeleted = isDeleted;
	}
}
