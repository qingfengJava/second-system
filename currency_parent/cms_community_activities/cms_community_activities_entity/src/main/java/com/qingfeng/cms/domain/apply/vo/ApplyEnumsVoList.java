package com.qingfeng.cms.domain.apply.vo;

import com.qingfeng.cms.domain.apply.ro.EnumsRo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "EnumsVoList", description = "枚举返回")
public class ApplyEnumsVoList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动规模")
    private List<EnumsRo> activeScale;

    @ApiModelProperty(value = "活动层级")
    private List<EnumsRo> activeLevel;

    @ApiModelProperty(value = "是否限制报名人数")
    private List<EnumsRo> isQuota;

    @ApiModelProperty(value = "是否需要主办方单独进行加分文件上传")
    private List<EnumsRo> isBonusPointsApply;

    @ApiModelProperty(value = "申请状态")
    private List<EnumsRo> agreeStatus;

    @ApiModelProperty(value = "活动状态")
    private List<EnumsRo> activeStatus;

    @ApiModelProperty(value = "是否发布")
    private List<EnumsRo> isRelease;
}
