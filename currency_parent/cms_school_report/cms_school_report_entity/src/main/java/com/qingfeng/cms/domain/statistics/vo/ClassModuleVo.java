package com.qingfeng.cms.domain.statistics.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ClassModuleVo", description = "班级的方案模块下的学生参与人数")
public class ClassModuleVo {

    /**
     * x轴的值
     */
    private List<String> xData;

    /**
     * 真实的数据
     */
    private List<Integer> data;
}
