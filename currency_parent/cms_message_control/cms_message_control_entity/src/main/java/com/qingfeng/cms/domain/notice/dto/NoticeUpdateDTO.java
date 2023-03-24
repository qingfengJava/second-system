package com.qingfeng.cms.domain.notice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class NoticeUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键Id")
	@NotNull(message = "主键Id不能为空")
	private Long id;

	@ApiModelProperty(value = "发布人（用户）id（关联发布人信息）")
	private Long userId;

	@ApiModelProperty(value = "公告标题")
	@NotBlank(message = "公告标题不能为空")
	private String title;

	@ApiModelProperty(value = "公告作者")
	@NotBlank(message = "公告作者不能为空")
	private String publicName;

	@ApiModelProperty(value = "公告内容")
	@NotBlank(message = "公告内容不能为空")
	private String content;

	@ApiModelProperty(value = "发布人角色编码（code）")
	private String userCode;

	@ApiModelProperty(value = "阅读量")
	private Integer readNum;
}
