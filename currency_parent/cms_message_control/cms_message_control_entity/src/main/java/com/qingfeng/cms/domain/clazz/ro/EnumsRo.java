package com.qingfeng.cms.domain.clazz.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "EnumsVo", description = "枚举返回")
public class EnumsRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "描述")
    private String label;

    @ApiModelProperty(value = "值")
    private String value;
}
