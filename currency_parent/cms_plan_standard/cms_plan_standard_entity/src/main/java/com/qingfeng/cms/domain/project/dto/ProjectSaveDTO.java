package com.qingfeng.cms.domain.project.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.project.enums.ProjectTypeEnum;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@ApiModel(value = "ProjectSaveDTO", description = "项目实体")
@TableName("crrm_project")
public class ProjectSaveDTO extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "绑定的模块id（外键）")
	@NotNull(message = "模块Id不能为空")
	private Long moduleId;

	@ApiModelProperty(value = "项目的名字")
	@NotEmpty(message = "项目的名字不能为空")
	private String projectName;

	@ApiModelProperty(value = "项目的备注内容")
	private String remarks;

	@ApiModelProperty(value = "院系  每个学院下还可以有独立的项目（用固定的字符串进行表示院系，比如：数计（sj））")
	private String department;

	@ApiModelProperty(value = "项目类型  （校级、院级、一般项目   采用固定字符串表示）")
	@NotEmpty(message = "项目类型不能为空")
	@Enumerated(EnumType.STRING)
	private ProjectTypeEnum projectType;

	@ApiModelProperty(value = "是否审核通过（用于二级学院提交的数据审核）")
	private Integer isCheck;

	@ApiModelProperty(value = "审核结果，不审核的就是null")
	private String checkDetail;

}
