package com.qingfeng.cms.domain.rule.dto;

import com.qingfeng.cms.domain.rule.enums.RuleCheckEnum;
import com.qingfeng.currency.base.entity.SuperEntity;
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
 * 加分（学分）细则表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CreditRulesCheckDTO", description = "学分细则审核实体")
public class CreditRulesCheckDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	@ApiModelProperty(value = "是否通过审核，只有院级一下才需要审核，默认通过")
	@NotNull(message = "审核结果不能为空")
	private RuleCheckEnum isCheck;

	@ApiModelProperty(value = "审核的详情，没有就是无")
	@NotEmpty(message = "审核详情不能为空")
	private String checkDetail;
}


