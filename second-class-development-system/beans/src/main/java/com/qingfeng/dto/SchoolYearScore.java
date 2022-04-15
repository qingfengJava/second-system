package com.qingfeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 学生学年学分封装类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SchoolYearScore {

    /**
     * 各学年学分集合
     */
    private List<Double> scores;
    /**
     * 学制
     */
    private Integer educationalSystem;
}
