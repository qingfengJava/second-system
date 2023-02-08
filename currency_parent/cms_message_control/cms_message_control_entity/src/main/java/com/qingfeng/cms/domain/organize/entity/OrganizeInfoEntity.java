package com.qingfeng.cms.domain.organize.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.organize.enums.OrganizeLevelEnum;
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
 * 社团组织详情表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OrganizeInfoEntity", description = "社团组织详情实体")
@TableName("mc_organize_info")
public class OrganizeInfoEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "社团用户id")
	private Long userId;

	@ApiModelProperty(value = "社团名称")
	private String organizeName;

	@ApiModelProperty(value = "社团所属组织")
	private String organizeDepartment;

	@ApiModelProperty(value = "社团级别（校级，院级）")
	private OrganizeLevelEnum organizeLevel;

	@ApiModelProperty(value = "社团介绍")
	private String organizeIntroduce;

	@ApiModelProperty(value = "社团指导老师")
	private String teacherName;

	@ApiModelProperty(value = "社团介绍视频")
	private String video;

	@ApiModelProperty(value = "成立时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate birthTime;

	@Builder
	public OrganizeInfoEntity(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime,
							  Long updateUser, Long userId, String organizeName, String organizeDepartment,
							  OrganizeLevelEnum organizeLevel, String organizeIntroduce, String teacherName,
							  String video, LocalDate birthTime) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.organizeName = organizeName;
		this.organizeDepartment = organizeDepartment;
		this.organizeLevel = organizeLevel;
		this.organizeIntroduce = organizeIntroduce;
		this.teacherName = teacherName;
		this.video = video;
		this.birthTime = birthTime;
	}
}
