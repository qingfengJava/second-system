package com.qingfeng.sms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ShortMessageEntity", description = "封装发送短信需要的实体")
public class ShortMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "要发送的手机号")
    @NotEmpty(message = "手机号不能为空")
    private String phoneNumber;

}
