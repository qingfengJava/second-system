package com.qingfeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 学生报名活动的条件查询字段封装
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationActive {

    /**
     * 学年
     */
    private String schoolYear;
    /**
     * 活动类型
     */
    private String activeType;
    /**
     * 申办单位
     */
    private String hostName;
    /**
     * 活动名称
     */
    private String activeName;
}
