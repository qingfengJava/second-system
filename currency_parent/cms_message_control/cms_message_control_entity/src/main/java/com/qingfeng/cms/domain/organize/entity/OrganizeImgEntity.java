package com.qingfeng.cms.domain.organize.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 社团组织图片信息
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrganizeImgEntity", description = "社团组织图片信息实体")
@TableName("mc_organize_img")
public class OrganizeImgEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "外键，社团部门Id")
	private Long organizeId;

	@ApiModelProperty(value = "轮播图路径")
	private String imgUrl;

	@ApiModelProperty(value = "状态，是否启用（枚举状态）")
	private Integer status;

	@Builder
	public OrganizeImgEntity(Long id, LocalDateTime createTime, Long createUser,
							 LocalDateTime updateTime, Long updateUser,
							 Long organizeId, String imgUrl, Integer status) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.organizeId = organizeId;
		this.imgUrl = imgUrl;
		this.status = status;
	}
}
