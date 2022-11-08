package com.qingfeng.cms.domain.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.enums.ProjectTypeEnum;
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
@ApiModel(value = "ProjectEntity", description = "项目实体")
@TableName("crrm_project")
public class ProjectEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "绑定的模块id（外键）")
	private Long moduleId;

	@ApiModelProperty(value = "项目的名字")
	private String projectName;

	@ApiModelProperty(value = "项目的备注内容")
	private String remarks;

	@ApiModelProperty(value = "院系  每个学院下还可以有独立的项目（用固定的字符串进行表示院系，比如：数计（sj））")
	private String department;

	@ApiModelProperty(value = "项目类型  （校级、院级、一般项目   采用固定字符串表示）")
	private ProjectTypeEnum projectType;

	@ApiModelProperty(value = "是否审核通过（用于二级学院提交的数据审核）枚举类型")
	private ProjectCheckEnum isCheck;

	@ApiModelProperty(value = "审核结果，不审核的就是null")
	private String checkDetail;

	@ApiModelProperty(value = "是否启用（0：未启用 1：启用）")
	private Integer isEnable;

	@Builder
	public ProjectEntity(Long id, LocalDateTime createTime, Long createUser,
						 LocalDateTime updateTime, Long updateUser, Long moduleId,
						 String projectName, String remarks, String department,
						 ProjectTypeEnum projectType, ProjectCheckEnum isCheck, String checkDetail,
						 Integer isEnable) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.moduleId = moduleId;
		this.projectName = projectName;
		this.remarks = remarks;
		this.department = department;
		this.projectType = projectType;
		this.isCheck = isCheck;
		this.checkDetail = checkDetail;
		this.isEnable = isEnable;
	}
}
