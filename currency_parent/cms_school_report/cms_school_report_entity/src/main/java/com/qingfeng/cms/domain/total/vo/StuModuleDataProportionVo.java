package com.qingfeng.cms.domain.total.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "StuModuleDataProportionVo", description = "模块项目数统计")
public class StuModuleDataProportionVo {

    @ApiModelProperty(value = "值")
    private Integer value;

    @ApiModelProperty(value = "模块名")
    private String name;
}
