package com.qingfeng.cms.domain.clazz.dto;

import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.student.enums.EducationalSystemEnum;
import com.qingfeng.cms.domain.student.enums.StudentTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 班级信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-12 22:37:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ClazzInfoEntity", description = "班级信息")
public class ClazzInfoSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "院系信息，枚举值")
    @NotNull(message = "院系信息不能为空")
    private DictDepartmentEnum department;

    @ApiModelProperty(value = "专业信息，枚举值")
    @NotBlank(message = "专业信息不能为空")
    private String major;

    @ApiModelProperty(value = "班级信息")
    @NotBlank(message = "班级信息不能为空")
    private String clazz;

    @ApiModelProperty(value = "年级")
    @NotBlank(message = "年级信息不能为空")
    private String grade;

    @ApiModelProperty(value = "学制")
    @NotNull(message = "学制不能为空")
    private EducationalSystemEnum educationalSystem;

    @ApiModelProperty(value = "班长名")
    @NotBlank(message = "班长名不能为空")
    private String clazzMonitor;

    @ApiModelProperty(value = "辅导员名")
    @NotBlank(message = "辅导员名不能为空")
    private String assistant;

    @ApiModelProperty(value = "班级描述")
    @NotBlank(message = "班级描述不能为空")
    private String clazzDescribe;

    @ApiModelProperty(value = "班级类型，本科、专科")
    @NotNull(message = "班级类型")
    private StudentTypeEnum clazzType;

}
