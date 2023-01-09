package com.qingfeng.currency.authority.entity.auth.vo;

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
 * @date 2023/1/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserRoleVo", description = "用户角色实体")
public class UserRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色Id")
    private Long roleId;

    @ApiModelProperty(value = "角色编码")
    private String code;
}
