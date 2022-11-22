package com.qingfeng.cms.domain.news.entity;

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
@ApiModel(value = "NewsNotifyEntity", description = "消息通知实体")
@TableName("mc_news_notify")
public class NewsNotifyEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "消息针对的用户Id")
	private Long userId;

	@ApiModelProperty(value = "消息内容  如：XXX同学，你的某某活动即将截止，请及时评价加分，避免活动失效！！！")
	private String newsContent;

	@ApiModelProperty(value = "是否已查看 （未查看   已查看）  查看了就不再做显示（枚举处理）")
	private String isSee;

	@Builder
	public NewsNotifyEntity(Long id, LocalDateTime createTime, Long createUser,
							LocalDateTime updateTime, Long updateUser, Long userId,
							String newsContent, String isSee) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.newsContent = newsContent;
		this.isSee = isSee;
	}
}
