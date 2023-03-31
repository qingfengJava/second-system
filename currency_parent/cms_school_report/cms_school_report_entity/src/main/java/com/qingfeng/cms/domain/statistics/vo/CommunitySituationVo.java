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
 * @date 2023/3/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "CommunitySituationVo")
public class CommunitySituationVo {

    /**
     * x轴的值
     */
    private List<String> xData;

    /**
     * 总活动数量
     */
    private List<Integer> actNum;

    /**
     * 大型活动数量
     */
    private List<Integer> bigActNum;

    /**
     * 中型活动数量
     */
    private List<Integer> mediumActNum;

    /**
     * 小型活动数量
     */
    private List<Integer> smallActNum;
}
