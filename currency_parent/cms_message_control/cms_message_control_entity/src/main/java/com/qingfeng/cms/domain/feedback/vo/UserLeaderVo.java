package com.qingfeng.cms.domain.feedback.vo;

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
@ApiModel(value = "UserLeaderVo", description = "用户对应的领导")
public class UserLeaderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "领导用户编码")
    private String userCode;
    @ApiModelProperty(value = "领导用户名字")
    private String userName;
}
