package com.qingfeng.cms.domain.bonus.dto;

import com.qingfeng.cms.domain.bonus.enums.BonusStatusEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "BonusScoreApplySaveDTO", description = "加分申报实体")
public class BonusScoreApplyPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块Id")
    private Long moduleId;

    @ApiModelProperty(value = "加分申请的一个状态（这里的审核通过是最终审核通过）  （0：申请中   1：审核通过）")
    private BonusStatusEnums bonusStatus;

    @ApiModelProperty(value = "学年——学期")
    private String schoolYear;

    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    @NotNull(message = "每页条目数不能为空")
    private Integer pageSize;
}
