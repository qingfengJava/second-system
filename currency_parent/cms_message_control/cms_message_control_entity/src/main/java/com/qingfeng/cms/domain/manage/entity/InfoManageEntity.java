package com.qingfeng.cms.domain.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.manage.enums.InfoTypeEnum;
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
import java.util.Date;

/**
 * 
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-12-30 17:01:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "InfoManageEntity", description = "信息管理")
@TableName("mc_info_manage")
public class InfoManageEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "维护对象类型，方便以后拓展（学生，教师）枚举处理")
	private InfoTypeEnum type;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@Builder
	public InfoManageEntity(Long id, LocalDateTime createTime, Long createUser,
							LocalDateTime updateTime, Long updateUser, InfoTypeEnum type,
							Date startTime, Date endTime) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
