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
 * @date 2023/3/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "GradeScoreRo")
public class GradeScoreRo {

    /**
     * 每列的名字
     */
    private String name;

    /**
     * 数据
     */
    private List<Integer> data;
}
