package com.qingfeng.cms.domain.project.dto;

import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.enums.ProjectTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProjectQueryDTO",description = "项目模块实体")
public class ProjectQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "绑定的模块id（外键）")
    @NotNull(message = "未选择对应的模块")
    private Long moduleId;

    @ApiModelProperty(value = "项目的名字")
    private String projectName;

    @ApiModelProperty(value = "院系  每个学院下还可以有独立的项目（用固定的字符串进行表示院系，比如：数计（sj））")
    private String department;

    @ApiModelProperty(value = "项目类型  （校级、院级、一般项目   采用固定字符串表示）")
    private ProjectTypeEnum projectType;

    @ApiModelProperty(value = "审核状态（INIT、IS_FINISHED、FAILED）")
    private ProjectCheckEnum isCheck;

    @ApiModelProperty(value = "是否启用（0：未启用 1：启用）")
    private Integer isEnable;
}
