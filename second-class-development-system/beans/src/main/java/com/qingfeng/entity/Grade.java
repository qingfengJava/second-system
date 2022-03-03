package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 社团评级实体类
 *
 * @author 清风学Java
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Grade {
    /**
     * 社团评级表Id
     */
    @Id
    @Column(name = "grade_id")
    private Integer gradeId;

    /**
     * 社团Id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 学年
     */
    @Column(name = "school_year")
    private String schoolYear;

    /**
     * 年度活动个数
     */
    @Column(name = "active_num")
    private Integer activeNum;

    /**
     * 精品活动个数
     */
    @Column(name = "best_active_num")
    private Integer bestActiveNum;

    /**
     * 活动综合评价级别（星级）
     */
    @Column(name = "total_evaluation_level")
    private Integer totalEvaluationLevel;

    /**
     * 评价级别（星级）
     */
    @Column(name = "grade_level")
    private Integer gradeLevel;

    /**
     * 评价时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 评价人姓名
     */
    private String appraiser;

    /**
     * 评价人职位
     */
    private String job;
}