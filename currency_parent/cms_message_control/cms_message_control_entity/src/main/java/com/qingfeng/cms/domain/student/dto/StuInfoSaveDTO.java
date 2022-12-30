package com.qingfeng.cms.domain.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.student.enums.EducationalSystemEnum;
import com.qingfeng.cms.domain.student.enums.HuKouTypeEnum;
import com.qingfeng.cms.domain.student.enums.IsChangeEnum;
import com.qingfeng.cms.domain.student.enums.PoliticsStatusEnum;
import com.qingfeng.cms.domain.student.enums.StateSchoolEnum;
import com.qingfeng.cms.domain.student.enums.StudentTypeEnum;
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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

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
@ApiModel(value = "StuInfoSaveDTO", description = "学生信息详情实体")
public class StuInfoSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键Id")
	private Long Id;

	@ApiModelProperty(value = "用户表外键，关联用户基础信息")
	@NotNull(message = "用户Id不能为空")
	private Long userId;

	@ApiModelProperty(value = "学号")
	@NotBlank(message = "学号不能为空")
	private String studentNum;

	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String studentName;

	@ApiModelProperty(value = "出生日期")
	private LocalDate birth;

	@ApiModelProperty(value = "民族")
	private String nation;

	@ApiModelProperty(value = "政治面貌")
	private PoliticsStatusEnum politicsStatus;

	@ApiModelProperty(value = "入学时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate enterTime;

	@ApiModelProperty(value = "毕业时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate graduateTime;

	@ApiModelProperty(value = "身份证号")
	private String idCard;

	@ApiModelProperty(value = "户口类型，枚举类型")
	private HuKouTypeEnum hukou;

	@ApiModelProperty(value = "QQ号")
	private String qq;

	@ApiModelProperty(value = "微信号")
	private String weChat;

	@ApiModelProperty(value = "籍贯")
	private String nativePlace;

	@ApiModelProperty(value = "家庭地址")
	private String address;

	@ApiModelProperty(value = "在校状态， 枚举类型")
	private StateSchoolEnum stateSchool;

	@ApiModelProperty(value = "学生类型， 枚举类型")
	private StudentTypeEnum type;

	@ApiModelProperty(value = "院系，使用数据字典的枚举类型")
	private DictDepartmentEnum department;

	@ApiModelProperty(value = "专业")
	private String major;

	@ApiModelProperty(value = "年级")
	private String grade;

	@ApiModelProperty(value = "班级")
	private String clazz;

	@ApiModelProperty(value = "学制")
	private EducationalSystemEnum educationalSystem;

	@ApiModelProperty(value = "个人描述")
	private String hobyDes;

	@ApiModelProperty(value = "是否可以修改 0-不可以修改  1-可以修改")
	private IsChangeEnum isChange;
}
