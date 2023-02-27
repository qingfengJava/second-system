package com.qingfeng.cms.domain.sign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
 * @date 2023/2/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveQueryDTO",description = "已发布活动查询条件实体")
public class ActiveQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年份")
    private Long userId;

    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    @NotNull(message = "每页条目数不能为空")
    private Integer pageSize;

//    @ApiModelProperty(value = "是否是最新的")
//    private Boolean isNew;
}
