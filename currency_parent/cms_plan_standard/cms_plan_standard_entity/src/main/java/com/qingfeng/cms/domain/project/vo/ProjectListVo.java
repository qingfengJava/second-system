package com.qingfeng.cms.domain.project.vo;

import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.enums.ProjectTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProjectListVo",description = "项目列表模块实体")
public class ProjectListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "绑定的模块id（外键）")
    private Long moduleId;

    @ApiModelProperty(value = "项目的名字")
    private String projectName;

    @ApiModelProperty(value = "项目的备注内容")
    private String remarks;

    @ApiModelProperty(value = "院系  每个学院下还可以有独立的项目（用固定的字符串进行表示院系，比如：数计（sj））")
    private String department;

    @ApiModelProperty(value = "项目类型  （校级、院级、一般项目   采用固定字符串表示）")
    private ProjectTypeEnum projectType;

    @ApiModelProperty(value = "是否审核通过（用于二级学院提交的数据审核）枚举类型")
    private ProjectCheckEnum isCheck;

    @ApiModelProperty(value = "审核结果，不审核的就是null")
    private String checkDetail;

    @ApiModelProperty(value = "是否启用（0：未启用 1：启用）")
    private Integer isEnable;
}
