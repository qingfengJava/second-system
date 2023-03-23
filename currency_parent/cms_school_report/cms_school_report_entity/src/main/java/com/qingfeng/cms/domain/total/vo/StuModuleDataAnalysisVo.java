package com.qingfeng.cms.domain.total.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "StuModuleDataAnalysisVo", description = "模块得分统计")
public class StuModuleDataAnalysisVo {

    @ApiModelProperty(value = "方案模块名")
    private List<String> moduleNames;

    @ApiModelProperty(value = "模块对应的学分")
    private List<Double> moduleScore;

    @ApiModelProperty(value = "模块下的活动/项目个数")
    private List<Integer> itemNum;
}
