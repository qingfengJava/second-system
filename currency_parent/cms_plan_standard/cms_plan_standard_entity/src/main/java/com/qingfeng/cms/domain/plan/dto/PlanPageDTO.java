package com.qingfeng.cms.domain.plan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class PlanPageDTO{

    @ApiModelProperty(value = "方案名")
    private String planName;

    @ApiModelProperty(value = "年份（按年设计，每一年的标准不一样）")
    private Integer year;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "应用对象 （1：本科   2：专科）")
    private Integer applicationObject;

    @ApiModelProperty(value = "是否启用改方案（0：未启用   1：启用）")
    private Integer isEnable;

}
