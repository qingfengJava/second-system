package com.qingfeng.cms.domain.bonus.ro;

import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.bonus.enums.BonusCheckStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@ApiModel(value = "BonusCheckVo", description = "活动加分审核实体")
public class BonusCheckRo {

    @ApiModelProperty(value = "加分审核主键Id")
    private Long id;

    @ApiModelProperty(value = "活动申请表Id")
    private Long applyId;

    @ApiModelProperty(value = "审核状态  枚举（待审核、已通过、未通过）")
    private BonusCheckStatusEnum checkStatus;

    @ApiModelProperty(value = "审核建议")
    private String checkContent;

    @ApiModelProperty(value = "活动名")
    private String activeName;

    @ApiModelProperty(value = "活动层级 （1：校级活动  2：院级活动  3：一般活动） 枚举")
    private ActiveLevelEnum activeLevel;

    @ApiModelProperty(value = "加分文件连接   默认为空")
    private String bonusFile;

    @ApiModelProperty(value = "活动状态  枚举（待开始，进行中，已结束，废弃）")
    private ActiveStatusEnum activeStatus;
}
