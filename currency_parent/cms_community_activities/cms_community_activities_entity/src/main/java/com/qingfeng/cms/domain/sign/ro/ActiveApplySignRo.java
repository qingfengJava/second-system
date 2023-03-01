package com.qingfeng.cms.domain.sign.ro;

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

import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveApplySignRo", description = "学生用户查询活动报名的实体")
public class ActiveApplySignRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "社团信息")
    private OrganizeInfoEntity organizeInfo;

    @ApiModelProperty(value = "活动信息")
    private ApplyEntity apply;

    @ApiModelProperty(value = "用户报名信息")
    private ActiveSignEntity activeSign;
}
