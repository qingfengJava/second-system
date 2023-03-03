package com.qingfeng.cms.domain.bonus.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.bonus.enums.BonusCheckStatusEnum;
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
@ApiModel(value = "BonusCheckEntity", description = "加分文件审核表")
@TableName("ca_bonus_check")
public class BonusCheckSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	@NotNull(message = "加分审核Id不能为空")
	private Long id;

	@ApiModelProperty(value = "审核状态  枚举（待审核、已通过、未通过）")
	@NotNull(message = "审核状态不能为空")
	private BonusCheckStatusEnum checkStatus;

	@ApiModelProperty(value = "审核建议")
	@NotBlank(message = "审核建议不能为空")
	private String checkContent;

}
