package com.qingfeng.cms.domain.plan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PlanSaveDTO", description = "方案")
public class PlanSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotEmpty(message = "方案名不能为空")
    @ApiModelProperty(value = "方案名")
    private String planName;

    @NotNull(message = "修读学分不能为空")
    @ApiModelProperty(value = "总共需要修读的学分")
    private Integer totalScore;

    @NotNull(message = "年份不能为空")
    @ApiModelProperty(value = "年份（按年设计，每一年的标准不一样）")
    private Integer year;

    @NotEmpty(message = "年级不能为空")
    @ApiModelProperty(value = "年级")
    private String grade;

    @NotNull(message = "应用对象不能为空")
    @ApiModelProperty(value = "应用对象 （1：本科   2：专科）")
    private Integer applicationObject;

    @NotNull(message = "是否启用不能为空")
    @ApiModelProperty(value = "是否启用改方案（0：未启用   1：启用）")
    private Integer isEnable;

    @ApiModelProperty(value = "方案说明")
    private String planContent;

}
