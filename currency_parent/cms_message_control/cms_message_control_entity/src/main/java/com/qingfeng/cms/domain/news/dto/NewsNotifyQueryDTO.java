package com.qingfeng.cms.domain.news.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 消息通知表
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
@ApiModel(value = "NewsNotifyQueryDTO", description = "消息通知保存实体")
public class NewsNotifyQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "消息的类型")
	private String newsType;

	@ApiModelProperty(value = "是否已查看 （未查看   已查看）  查看了就不再做显示（枚举处理）")
	private String isSee;
}
