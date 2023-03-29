package com.qingfeng.cms.domain.statistics.vo;

import com.qingfeng.cms.domain.statistics.ro.GradeScoreRo;
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
 * @date 2023/3/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "GradeScoreVo", description = "各年级学分修读情况")
public class GradeScoreVo {

    /**
     * X轴数据
     */
    private List<String> xAxis;

    /**
     * 柱状图数据
     */
    private List<GradeScoreRo> gradeScoreRoList;
}
