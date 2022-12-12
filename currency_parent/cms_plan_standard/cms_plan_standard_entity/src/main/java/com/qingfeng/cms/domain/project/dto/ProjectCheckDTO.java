package com.qingfeng.cms.domain.project.dto;

import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
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
 * 项目表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProjectSaveDTO", description = "项目实体")
public class ProjectCheckDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
	private Long id;

	@ApiModelProperty(value = "是否审核通过（用于二级学院提交的数据审核）枚举类型")
	@NotNull(message = "项目审核枚举类型不能为空")
	private ProjectCheckEnum isCheck;

	@ApiModelProperty(value = "审核结果，不审核的就是null")
	@NotEmpty(message = "审核结果不能为空")
	private String checkDetail;

}
