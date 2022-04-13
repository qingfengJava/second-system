package com.qingfeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 各种类型活动的数量
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TypeActiveNum {

    /**
     * 思想道德活动数量
     */
    private Integer moralNum;
    /**
     * 学术创新活动数量
     */
    private Integer academicNum;
    /**
     * 文化交往活动数量
     */
    private Integer cultureNum;
    /**
     * 社团工作活动数量
     */
    private Integer clubWork;
    /**
     * 社会志愿服务活动数量
     */
    private Integer volunteerNum;
    /**
     * 技能其他活动数量
     */
    private Integer otherSkill;
}
