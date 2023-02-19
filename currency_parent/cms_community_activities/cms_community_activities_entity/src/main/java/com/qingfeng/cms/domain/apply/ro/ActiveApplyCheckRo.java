package com.qingfeng.cms.domain.apply.ro;

import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.currency.base.entity.SuperEntity;
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
import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveApplyCheckRo", description = "活动申请审核信息")
public class ActiveApplyCheckRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    @ApiModelProperty(value = "申请状态  枚举（申请中、已通过、未通过）")
    @NotNull(message = "活动申请审核的结果不能为空")
    private AgreeStatusEnum agreeStatus;

    @ApiModelProperty(value = "活动申请审核的评语，用于发送通知时使用")
    @NotBlank(message = "活动申请审核的评语不能为空")
    private String checkContents;

}
