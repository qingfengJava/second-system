package com.qingfeng.cms.domain.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(description = "项目实体")
@TableName("crrm_project")
public class ProjectEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目id")
	@TableId(value = "project_id", type = IdType.AUTO)
	private Long projectId;

	@ApiModelProperty(value = "绑定的模id（外键）")
	private Long moduleId;

	@ApiModelProperty(value = "项目的名字")
	private String projectName;

	@ApiModelProperty(value = "项目的备注内容")
	private String remarks;

	@ApiModelProperty(value = "院系  每个学院下还可以有独立的项目（用固定的字符串进行表示院系，比如：数计（sj））")
	private String department;

	@ApiModelProperty(value = "项目类型  （校级、院级、一般项目   采用固定字符串表示）")
	private String projectType;

	@ApiModelProperty(value = "是否审核通过（用于二级学院提交的数据审核）")
	private Integer isCheck;

	@ApiModelProperty(value = "审核结果，不审核的就是null")
	private String checkDetail;

	@ApiModelProperty(value = "创建的时间")
	@TableField(fill = FieldFill.INSERT)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@ApiModelProperty(value = "修改的时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	@TableLogic
	@ApiModelProperty(value = "是否删除（0：未删除  1：已删除） 逻辑删除")
	@TableField(fill = FieldFill.INSERT)
	private Integer isDelete;

}
