package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "organize_id")
    private Integer organizeId;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 评级人姓名
     */
    private String appraiser;

    /**
     * 评级人职位
     */
    private String job;

    /**
     * 评级内容总结
     */
    private String content;
}