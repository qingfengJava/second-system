package com.qingfeng.cms.domain.apply.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BonusFileVo", description = "加分实体")
public class BonusFileVo {

    @ApiModelProperty(value = "活动主键Id")
    @NotNull(message = "活动主键Id不能为空")
    private Long id;

    @ApiModelProperty(value = "加分文件链接")
    @NotBlank(message = "加分文件链接不能为空")
    private String bonusFile;
}
