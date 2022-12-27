package com.qingfeng.cms.domain.plan.vo;

import com.qingfeng.cms.domain.module.vo.CreditModuleVo;
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
 * @date 2022/10/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PlanEntityVo", description = "方案")
public class PlanEntityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父级Id，没有默认为0")
    private Long parentId;

    @ApiModelProperty(value = "方案名")
    private String planName;

    @ApiModelProperty(value = "总共需要修读的学分")
    private Integer totalScore;

    @ApiModelProperty(value = "年份（按年设计，每一年的标准不一样）")
    private Integer year;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "应用对象 （1：本科   2：专科  3：研究生）")
    private Integer applicationObject;

    @ApiModelProperty(value = "是否启用改方案（0：未启用   1：启用）")
    private Integer isEnable;

    @ApiModelProperty(value = "方案说明")
    private String planContent;

    @ApiModelProperty(value = "课堂方针（第二课堂文件的连接）")
    private String classPolicy;

    @ApiModelProperty(value = "课堂方针详情")
    private String classDetails;

    @ApiModelProperty(value = "学分认定模块实体集合")
    private List<CreditModuleVo> creditModuleVoList;

}
