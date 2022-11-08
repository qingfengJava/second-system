package com.qingfeng.cms.domain.plan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@ApiModel(value = "PlanPageDTO", description = "方案")
public class PlanPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "方案名")
    private String planName;

    @ApiModelProperty(value = "年份（按年设计，每一年的标准不一样）")
    private Integer year;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "应用对象 （1：本科   2：专科）")
    private Integer applicationObject;

    @ApiModelProperty(value = "是否启用改方案（0：未启用   1：启用）")
    @Min(0)
    @Max(1)
    private Integer isEnable;

}
