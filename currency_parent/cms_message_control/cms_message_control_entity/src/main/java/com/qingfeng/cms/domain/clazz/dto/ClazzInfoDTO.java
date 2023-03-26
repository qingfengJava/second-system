package com.qingfeng.cms.domain.clazz.dto;

import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
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
public class ClazzInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "院系信息，枚举值")
    @NotNull(message = "院系不能为空")
    private DictDepartmentEnum department;

    @ApiModelProperty(value = "专业信息，枚举值")
    @NotBlank(message = "专业不能为空")
    private String major;

    @ApiModelProperty(value = "班级信息")
    @NotBlank(message = "班级信息不能为空")
    private String clazz;

    @ApiModelProperty(value = "年级")
    @NotBlank(message = "年级不能为空")
    private String grade;
}
