package com.qingfeng.cms.domain.feedback.dto;

import com.qingfeng.cms.domain.feedback.enums.IsReceiveEnum;
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
 * 系统反馈表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SystemFeedbackQueryDTO", description = "系统反馈保存实体")
public class SystemFeedbackQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "满意度（1~5代表不同的星级评价）")
	private Integer systemEvaluation;

	@ApiModelProperty(value = "反馈对象类型")
	private String feedbackType;

	@ApiModelProperty(value = "是否已回复（未回复   已回复）（枚举类型）")
	private IsReceiveEnum isReceive;

	private Integer current;
	private Integer size;
}
