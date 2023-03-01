package com.qingfeng.cms.domain.sign.dto;

import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.sign.enums.EvaluationStatusEnum;
import com.qingfeng.cms.domain.sign.enums.SignStatusEnum;
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
@ApiModel(value = "ActiveApplySignQueryDTO",description = "学生查询已报名的活动的条件实体")
public class ActiveApplySignQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "活动层级 （1：校级活动  2：院级活动  3：一般活动） 枚举")
    private ActiveLevelEnum activeLevel;

    @ApiModelProperty(value = "活动状态  枚举（待开始，进行中，已结束，废弃）")
    private ActiveStatusEnum activeStatus;

    @ApiModelProperty(value = "签到状态   枚举值（待签到、已签到、超时废弃）")
    private SignStatusEnum signStatus;

    @ApiModelProperty(value = "活动评价状态 （待评价，已评价，超时废弃）")
    private EvaluationStatusEnum evaluationStatus;

    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    @NotNull(message = "每页条目数不能为空")
    private Integer pageSize;

}
