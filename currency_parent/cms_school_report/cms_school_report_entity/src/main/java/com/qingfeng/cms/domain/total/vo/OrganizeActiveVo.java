package com.qingfeng.cms.domain.total.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 社团活动举办情况
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "OrganizeActiveVo", description = "社团活动举办情况")
public class OrganizeActiveVo {

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

    /**
     * 申请中的活动数量
     */
    public List<Integer> applyActNum;

    /**
     * 进行中的活动数量
     */
    public List<Integer> inActNum;

    /**
     * 已完成的活动数量
     */
    public List<Integer> completeActNum;

    /**
     * 废弃的活动数量
     */
    public List<Integer> abandonActNum;

}
