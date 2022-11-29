package com.qingfeng.cms.domain.college.entity;

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
 * 院级信息（包含班级），数据字典
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
@ApiModel(value="CollegeInformationEntity", description = "院级信息（包含班级），数据字典实体")
@TableName("mc_college_Information")
public class CollegeInformationEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户Id")
	private Long userId;

	@ApiModelProperty(value = "数据字典主键Id")
	private Long dictId;

	@ApiModelProperty(value = "组织名字   比如：攀枝花学院，数学与计算机学院， 计算机科学与技术班")
	private String organizationName;

	@ApiModelProperty(value = "组织编号， 比如攀枝花学院（pzhu）")
	private String organizationCode;

	@Builder
	public CollegeInformationEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
									Long updateUser, Long userId, Long dictId, String organizationName,
									String organizationCode) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.dictId = dictId;
		this.organizationName = organizationName;
		this.organizationCode = organizationCode;
	}
}
