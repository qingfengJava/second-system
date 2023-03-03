package com.qingfeng.cms.domain.apply.dto;

import com.qingfeng.cms.domain.apply.enums.ApplyCheckStatusEnum;
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
 * 加分文件审核表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ActiveCheckQueryDTO", description = "活动终审查询")
public class ActiveCheckQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动名")
	private String activeName;

	@ApiModelProperty(value = "审核状态  枚举（待审核、已通过、未通过）")
	private ApplyCheckStatusEnum checkStatus;

	@ApiModelProperty(value = "页码")
	@NotNull(message = "页码不能为空")
	private Integer pageNo;

	@ApiModelProperty(value = "每页条目数")
	@NotNull(message = "每页条目数不能为空")
	private Integer pageSize;

}
