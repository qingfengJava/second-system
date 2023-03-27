package com.qingfeng.cms.domain.statistics.ro;

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
 * @date 2023/3/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "StuSemesterCreditsRo", description = "学生学期等分情况")
public class StuSemesterCreditsRo {

    /**
     * 名字
     */
    private String name;

    private String type = "line";

    private String stack = "Total";

    /**
     * 每一条折线对应的数据
     */
    private List<Integer> data;
}
