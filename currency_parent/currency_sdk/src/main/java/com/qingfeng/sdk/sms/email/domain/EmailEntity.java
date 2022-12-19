package com.qingfeng.sdk.sms.email.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
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
@ApiModel(value = "EmailEntity", description = "封装发送邮件需要的实体")
public class EmailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "发送的邮件号不能为空")
    @Email
    @ApiModelProperty(value = "发送的邮件号")
    private String email;

    @NotEmpty(message = "邮件的标题不能为空")
    @ApiModelProperty(value = "邮件的标题")
    private String title;

    @NotEmpty(message = "邮件的内容不能为空")
    @ApiModelProperty(value = "邮件的内容")
    private String body;

    @ApiModelProperty(value = "唯一标识Key")
    private String key;
}
