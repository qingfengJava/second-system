package com.qingfeng.cms.domain.check.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "PlanModuleVo", description = "方案模块树实体")
public class PlanModuleVo {

    @ApiModelProperty(value = "显示的值")
    private String label;

    @ApiModelProperty(value = "id值")
    private Long value;

    /**
     * 子集
     */
    private List<PlanModuleVo> children;
}
