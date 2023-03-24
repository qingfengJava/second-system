package com.qingfeng.cms.domain.notice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class NoticeQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公告标题")
	private String title;

	@ApiModelProperty(value = "页码")
	@NotNull(message = "页码不能为空")
	private Integer pageNo;

	@ApiModelProperty(value = "每页条目数")
	@NotNull(message = "每页条目数不能为空")
	private Integer pageSize;
}
