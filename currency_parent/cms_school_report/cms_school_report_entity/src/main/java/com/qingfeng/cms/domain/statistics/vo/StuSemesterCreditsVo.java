package com.qingfeng.cms.domain.statistics.vo;

import com.qingfeng.cms.domain.statistics.ro.StuSemesterCreditsRo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 学生学期学分修读情况
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "StuSemesterCreditsVo", description = "学生学期等分情况")
public class StuSemesterCreditsVo {

    /**
     * x轴坐标：每一个学期
     */
    private List<String> schoolTerm;

    /**
     * 具体的数据
     */
    private List<StuSemesterCreditsRo> stuSemesterCreditsRoList;

    /**
     * 折线名称：
     *      所修学分：2
     *      社团活动类个数：5
     *      。。。其他模块
     */
    private List<String> legendData;
}
