package com.qingfeng.cms.domain.level.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
public class LevelEntity  extends Entity<Long> {

	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "关联的项目id")
	private Long projectId;

	@ApiModelProperty(value = "等级的名字或者是具体内容，没有就写无")
	private String levelContent;

	@ApiModelProperty(value = "是否审核通过，只有学院下的需要审核")
	private Integer isCheck;

	@ApiModelProperty(value = "审核的详情，不需要审核的就写无")
	private String checkDetail;

	@TableLogic
	@ApiModelProperty(value = "是否删除（0：不删除   1：已删除）")
	private Integer isDelete;

	@Builder
	public LevelEntity(Long id, LocalDateTime createTime, Long createUser,
					   LocalDateTime updateTime, Long updateUser, Long projectId,
					   String levelContent, Integer isCheck, String checkDetail,
					   Integer isDelete) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.projectId = projectId;
		this.levelContent = levelContent;
		this.isCheck = isCheck;
		this.checkDetail = checkDetail;
		this.isDelete = isDelete;
	}
}