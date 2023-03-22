package com.qingfeng.cms.domain.total.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "StudentScoreDetailsVo", description = "学生得分详情")
public class StudentScoreDetailsVo {

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "项目/活动名")
    private String projectActiveName;

    @ApiModelProperty(value = "等级")
    private String levelName;

    @ApiModelProperty(value = "学分细则")
    private String rule;

    @ApiModelProperty(value = "实际得分")
    private BigDecimal score;

    @ApiModelProperty(value = "学年——学期")
    private String schoolYear;
}
