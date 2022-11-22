package com.qingfeng.cms.domain.student.entity;

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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生信息详情表
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
@ApiModel(value = "StuInfoEntity", description = "学生信息详情实体")
@TableName("mc_stu_info")
public class StuInfoEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户表外键，关联用户基础信息")
	private Long userId;

	@ApiModelProperty(value = "学号")
	private String studentNum;

	@ApiModelProperty(value = "姓名")
	private String studentName;

	@ApiModelProperty(value = "出生日期")
	private LocalDate birth;

	@ApiModelProperty(value = "民族")
	private String nation;

	@ApiModelProperty(value = "政治面貌")
	private String politicsStatus;

	@ApiModelProperty(value = "入学时间")
	private LocalDate enterTime;

	@ApiModelProperty(value = "毕业时间")
	private LocalDate graduateTime;

	@ApiModelProperty(value = "身份证号")
	private String idCard;

	@ApiModelProperty(value = "户口类型")
	private String hukou;

	@ApiModelProperty(value = "QQ号")
	private String qq;

	@ApiModelProperty(value = "微信号")
	private String weChat;

	@ApiModelProperty(value = "籍贯")
	private String nativePlace;

	@ApiModelProperty(value = "家庭地址")
	private String address;

	@ApiModelProperty(value = "在校状态")
	private String stateSchool;

	@ApiModelProperty(value = "学生类型")
	private String type;

	@ApiModelProperty(value = "院系")
	private String department;

	@ApiModelProperty(value = "专业")
	private String major;

	@ApiModelProperty(value = "年级")
	private String grade;

	@ApiModelProperty(value = "班级")
	private String clazz;

	@ApiModelProperty(value = "学制")
	private Integer educationalSystem;

	@ApiModelProperty(value = "个人描述")
	private String hobyDes;

	@ApiModelProperty(value = "是否可以修改 0-不可以修改  1-可以修改")
	private Integer isChange;

	@Builder
	public StuInfoEntity(Long id, LocalDateTime createTime, Long createUser,
						 LocalDateTime updateTime, Long updateUser, Long userId,
						 String studentNum, String studentName, LocalDate birth,
						 String nation, String politicsStatus, LocalDate enterTime,
						 LocalDate graduateTime, String idCard, String hukou, String qq,
						 String weChat, String nativePlace, String address,
						 String stateSchool, String type, String department,
						 String major, String grade, String clazz, Integer educationalSystem,
						 String hobyDes, Integer isChange) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.userId = userId;
		this.studentNum = studentNum;
		this.studentName = studentName;
		this.birth = birth;
		this.nation = nation;
		this.politicsStatus = politicsStatus;
		this.enterTime = enterTime;
		this.graduateTime = graduateTime;
		this.idCard = idCard;
		this.hukou = hukou;
		this.qq = qq;
		this.weChat = weChat;
		this.nativePlace = nativePlace;
		this.address = address;
		this.stateSchool = stateSchool;
		this.type = type;
		this.department = department;
		this.major = major;
		this.grade = grade;
		this.clazz = clazz;
		this.educationalSystem = educationalSystem;
		this.hobyDes = hobyDes;
		this.isChange = isChange;
	}
}
