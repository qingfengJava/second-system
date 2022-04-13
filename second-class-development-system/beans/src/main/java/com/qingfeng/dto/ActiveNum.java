package com.qingfeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 活动数量统计
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActiveNum {

    /**
     * 一般活动数量
     */
    private Integer commonActive;
    /**
     * 院级活动数量
     */
    private Integer hospitalLevelActive;
    /**
     * 校级活动数量
     */
    private Integer schoolLevelActive;
}
