package com.qingfeng.cms.domain.sign.vo;

import com.qingfeng.cms.domain.sign.ro.ActiveApplySignRo;
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
 * @date 2023/3/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ActiveApplySignVo", description = "学生用户查询活动报名的实体")
public class ActiveApplySignVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总记录数")
    private Integer total;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条目数")
    private Integer pageSize;

    @ApiModelProperty(value = "学生报名的活动信息")
    private List<ActiveApplySignRo> activeApplySignRoList;
}
