package com.qingfeng.cms.domain.bonus.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

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
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BonusScorePageVo", description = "项目加分申请分页实体")
public class BonusScorePageVo {

    @ApiModelProperty(value = "总记录数")
    private Integer total;

    @ApiModelProperty(value = "活动申请列表")
    private List<BonusScoreApplyVo> bonusScoreApplyList;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    private Integer pageSize;
}
