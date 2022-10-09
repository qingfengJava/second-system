package com.qingfeng.cms.domain.module.entity;

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
 * 学分认定模块表
 * 
 * @author qingfeng
 * @email ${email}
 * @date 2022-10-08 19:44:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(description = "学分认定模块实体")
@TableName("crrm_credit_module")
public class CreditModuleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(value = "module_id", type = IdType.AUTO)
	@ApiModelProperty(value = "模块id")
	private Long moduleId;

	@ApiModelProperty(value = "外键  方案id")
	private Long planId;

	@ApiModelProperty(value = "模块名")
	private String moduleName;

	@ApiModelProperty(value = "模块的内容")
	private String moduleContent;

	@ApiModelProperty(value = "该模块最少修读的学分（各模块最低分相加不能超过总学分）")
	private Integer minScore;

	@ApiModelProperty(value = "年份（冗余字段，方便后面查询）")
	private Integer year;

	@ApiModelProperty(value = "年级（冗余字段，方便后面进行查询）")
	private String grade;

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
