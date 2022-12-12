package com.qingfeng.cms.domain.level.dto;

import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
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
 * 项目等级表
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
@ApiModel(value = "LevelCheckDTO",description = "项目等级审核模块实体")
public class LevelCheckDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	@ApiModelProperty(value = "是否审核通过，只有学院下的需要审核")
	@NotNull(message = "审核结果不能为空")
	private LevelCheckEnum isCheck;

	@ApiModelProperty(value = "审核的详情，不需要审核的就写无")
	@NotEmpty(message = "审核结果不能为空")
	private String checkDetail;
}
