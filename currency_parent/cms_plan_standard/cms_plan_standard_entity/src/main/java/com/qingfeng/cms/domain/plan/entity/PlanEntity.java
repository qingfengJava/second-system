package com.qingfeng.cms.domain.plan.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 方案设定表（是否是修读标准，本科标准，专科标准）
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
@ApiModel(description = "方案设定实体")
@TableName("crrm_plan")
public class PlanEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 方案主键Id
	 */
	@ApiModelProperty(value = "方案主键Id")
	@TableId(value = "plan_id", type = IdType.AUTO)
	private Long planId;

	@ApiModelProperty(value = "总共需要修读的学分")
	private Integer totalScore;

	@ApiModelProperty(value = "年份（按年设计，每一年的标准不一样）")
	private Integer year;

	@ApiModelProperty(value = "年级")
	private String grade;

	@ApiModelProperty(value = "应用对象 （1：本科   2：专科）")
	private Integer applicationObject;

	@ApiModelProperty(value = "是否启用改方案（0：未启用   1：启用）")
	private Integer isEnable;

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

}
