package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 学生得分成绩表
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Score {
    /**
     * 得分表主键Id
     */
    @Id
    @Column(name = "scoreId")
    private Integer scoreid;

    /**
     * 学生id
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 思想政治与道德修养类得分（1）
     */
    @Column(name = "ideology_politics_score")
    private Double ideologyPoliticsScore;

    /**
     * 学术科技与创新创业类得分（2）
     */
    @Column(name = "science_technology_score")
    private Double scienceTechnologyScore;

    /**
     * 文化艺术与交往能力得分（3）
     */
    @Column(name = "culture_art_score")
    private Double cultureArtScore;

    /**
     * 社团活动与社会工作领导能力类（4)
     */
    @Column(name = "club_job_score")
    private Double clubJobScore;

    /**
     * 社会实践与志愿服务类（5）
     */
    @Column(name = "social_volunteer_score")
    private Double socialVolunteerScore;

    /**
     * 技能培训与其他相关类（6）
     */
    @Column(name = "skill_train_score")
    private Double skillTrainScore;

    /**
     * 总得分
     */
    @Column(name = "total_score")
    private Double totalScore;
}