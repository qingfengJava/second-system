package com.qingfeng.cms.domain.apply.vo;

import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
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
 * @date 2023/4/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveApplyVo")
public class ActiveApplyVo {

    @ApiModelProperty(value = "活动申请信息")
    private ApplyEntity apply;

    @ApiModelProperty(value = "活动签到信息")
    private ActiveSignEntity activeSign;

    @ApiModelProperty(value = "社团组织信息")
    private OrganizeInfoEntity organizeInfo;
}
