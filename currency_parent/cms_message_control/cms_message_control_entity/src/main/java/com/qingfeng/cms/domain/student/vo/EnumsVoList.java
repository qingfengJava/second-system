package com.qingfeng.cms.domain.student.vo;

import com.qingfeng.cms.domain.student.ro.EnumsRo;
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
public class EnumsVoList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "政治面貌")
    private List<EnumsRo> politics;

    @ApiModelProperty(value = "在校状态")
    private List<EnumsRo> stateSchool;

    @ApiModelProperty(value = "户口类型")
    private List<EnumsRo> hukou;

    @ApiModelProperty(value = "学生类型")
    private List<EnumsRo> stuType;

    @ApiModelProperty(value = "学制")
    private List<EnumsRo> educationalSystem;

}