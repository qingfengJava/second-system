package com.qingfeng.cms.domain.notice.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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
@ApiModel(description = "系统公告实体")
public class NoticeRo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键Id")
	private Long id;

	@ApiModelProperty(value = "发布人（用户）id（关联发布人信息）")
	private Long userId;

	@ApiModelProperty(value = "公告标题")
	private String title;

	@ApiModelProperty(value = "公告作者")
	private String publicName;

	@ApiModelProperty(value = "用户标签，班级，学院。。。")
	private String tag;

	@ApiModelProperty(value = "阅读量")
	private Integer readNum;

	@ApiModelProperty(value = "更新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "是否查看")
	private Boolean isCheck;
}
