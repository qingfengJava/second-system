package com.qingfeng.cms.domain.total.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ModuleRo", description = "模块总得分情况")
public class ModuleRo {

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "已得学分")
    private BigDecimal creditsEarned;

    @ApiModelProperty(value = "应得学分")
    private BigDecimal creditsDue;
}
