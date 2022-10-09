package com.qingfeng.cms.domain.level.entity;

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
@ApiModel(description = "项目等级实体")
@TableName("crrm_level")
public class LevelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目等级Id")
	@TableId(value = "level_id", type = IdType.AUTO)
	private Long levelId;

	@ApiModelProperty(value = "关联的项目id")
	private Long projectId;

	@ApiModelProperty(value = "等级的名字或者是具体内容，没有就写无")
	private String levelContent;

	@ApiModelProperty(value = "是否审核通过，只有学院下的需要审核")
	private Integer isCheck;

	@ApiModelProperty(value = "审核的详情，不需要审核的就写无")
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
	@ApiModelProperty(value = "是否删除（0：不删除   1：已删除）")
	@TableField(fill = FieldFill.INSERT)
	private Integer isDelete;

}
