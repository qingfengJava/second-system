package com.qingfeng.cms.domain.sign.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveScaleEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.sign.enums.SignStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserActiveSignFrontVo")
public class UserActiveSignFrontVo {

    @ApiModelProperty(value = "活动的名称")
    private String activeName;

    @ApiModelProperty(value = "活动规模  小、中、大 （枚举）")
    private ActiveScaleEnum activeScale;

    @ApiModelProperty(value = "活动层级 （1：校级活动  2：院级活动  3：一般活动） 枚举")
    private ActiveLevelEnum activeLevel;

    @ApiModelProperty(value = "活动负责人")
    private String activePrincipal;

    @ApiModelProperty(value = "活动开始时间 ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate activeStartTime;

    @ApiModelProperty(value = "活动结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate activeEndTime;

    @ApiModelProperty(value = "学年——学期")
    private String schoolYear;

    @ApiModelProperty(value = "活动简介   申请活动时需要")
    private String activeIntroduction;

    @ApiModelProperty(value = "活动内容  在进行发布活动的时候需要")
    private String activeContent;

    @ApiModelProperty(value = "活动状态  枚举（待开始，进行中，已结束，废弃）")
    private ActiveStatusEnum activeStatus;

    @ApiModelProperty(value = "签到状态   枚举值（待签到、已签到、超时废弃）")
    private SignStatusEnum signStatus;
}
