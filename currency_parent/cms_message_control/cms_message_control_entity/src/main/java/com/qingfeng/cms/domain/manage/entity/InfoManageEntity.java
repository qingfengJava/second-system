package com.qingfeng.cms.domain.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.manage.enums.InfoTypeEnum;
import com.qingfeng.cms.domain.manage.enums.TypeStatusEnum;
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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "维护对象类型，方便以后拓展（学生，教师）枚举处理")
	private InfoTypeEnum type;

	@ApiModelProperty(value = "执行人")
	private String executor;

	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate startTime;

	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate endTime;

	@ApiModelProperty(value = "任务状态，待处理、处理中、已结束、已废弃")
	private TypeStatusEnum typeStatus;

	@Builder
	public InfoManageEntity(Long id, LocalDateTime createTime, Long createUser,
							LocalDateTime updateTime, Long updateUser, InfoTypeEnum type,
							LocalDate startTime, LocalDate endTime, TypeStatusEnum typeStatus) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
		this.typeStatus = typeStatus;
	}
}
