package com.qingfeng.cms.domain.check.dto;

import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
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
public class BonusScoreApplyCheckPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块Id")
    private Long moduleId;

    @ApiModelProperty(value = "班级审核状态")
    private CheckStatusEnums classStatus;

    @ApiModelProperty(value = "学年——学期")
    private String schoolYear;

    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    @NotNull(message = "每页条目数不能为空")
    private Integer pageSize;
}
