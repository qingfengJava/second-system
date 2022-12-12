package com.qingfeng.cms.domain.news.dto;

import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "NewsNotifySaveDTO", description = "消息通知保存实体")
public class NewsNotifySaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "消息针对的用户Id")
	@NotNull(message = "用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "消息的类型")
	@NotNull(message = "消息的类型不能为空")
	private NewsTypeEnum newsType;

	@ApiModelProperty(value = "消息的标题")
	@NotEmpty(message = "消息的标题不能为空")
	private String newsTitle;

	@ApiModelProperty(value = "消息内容  如：XXX同学，你的某某活动即将截止，请及时评价加分，避免活动失效！！！")
	@NotEmpty(message = "消息的内容不能为空")
	private String newsContent;

	@ApiModelProperty(value = "是否已查看 （未查看   已查看）  查看了就不再做显示（枚举处理）")
	private IsSeeEnum isSee;
}
