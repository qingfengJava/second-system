package com.qingfeng.entity;

import javax.persistence.*;

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

    /**
     * 获取得分表主键Id
     *
     * @return scoreId - 得分表主键Id
     */
    public Integer getScoreid() {
        return scoreid;
    }

    /**
     * 设置得分表主键Id
     *
     * @param scoreid 得分表主键Id
     */
    public void setScoreid(Integer scoreid) {
        this.scoreid = scoreid;
    }

    /**
     * 获取学生id
     *
     * @return userId - 学生id
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置学生id
     *
     * @param userid 学生id
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取思想政治与道德修养类得分（1）
     *
     * @return ideology_politics_score - 思想政治与道德修养类得分（1）
     */
    public Double getIdeologyPoliticsScore() {
        return ideologyPoliticsScore;
    }

    /**
     * 设置思想政治与道德修养类得分（1）
     *
     * @param ideologyPoliticsScore 思想政治与道德修养类得分（1）
     */
    public void setIdeologyPoliticsScore(Double ideologyPoliticsScore) {
        this.ideologyPoliticsScore = ideologyPoliticsScore;
    }

    /**
     * 获取学术科技与创新创业类得分（2）
     *
     * @return science_technology_score - 学术科技与创新创业类得分（2）
     */
    public Double getScienceTechnologyScore() {
        return scienceTechnologyScore;
    }

    /**
     * 设置学术科技与创新创业类得分（2）
     *
     * @param scienceTechnologyScore 学术科技与创新创业类得分（2）
     */
    public void setScienceTechnologyScore(Double scienceTechnologyScore) {
        this.scienceTechnologyScore = scienceTechnologyScore;
    }

    /**
     * 获取文化艺术与交往能力得分（3）
     *
     * @return culture_art_score - 文化艺术与交往能力得分（3）
     */
    public Double getCultureArtScore() {
        return cultureArtScore;
    }

    /**
     * 设置文化艺术与交往能力得分（3）
     *
     * @param cultureArtScore 文化艺术与交往能力得分（3）
     */
    public void setCultureArtScore(Double cultureArtScore) {
        this.cultureArtScore = cultureArtScore;
    }

    /**
     * 获取社团活动与社会工作领导能力类（4)
     *
     * @return club_job_score - 社团活动与社会工作领导能力类（4)
     */
    public Double getClubJobScore() {
        return clubJobScore;
    }

    /**
     * 设置社团活动与社会工作领导能力类（4)
     *
     * @param clubJobScore 社团活动与社会工作领导能力类（4)
     */
    public void setClubJobScore(Double clubJobScore) {
        this.clubJobScore = clubJobScore;
    }

    /**
     * 获取社会实践与志愿服务类（5）
     *
     * @return social_volunteer_score - 社会实践与志愿服务类（5）
     */
    public Double getSocialVolunteerScore() {
        return socialVolunteerScore;
    }

    /**
     * 设置社会实践与志愿服务类（5）
     *
     * @param socialVolunteerScore 社会实践与志愿服务类（5）
     */
    public void setSocialVolunteerScore(Double socialVolunteerScore) {
        this.socialVolunteerScore = socialVolunteerScore;
    }

    /**
     * 获取技能培训与其他相关类（6）
     *
     * @return skill_train_score - 技能培训与其他相关类（6）
     */
    public Double getSkillTrainScore() {
        return skillTrainScore;
    }

    /**
     * 设置技能培训与其他相关类（6）
     *
     * @param skillTrainScore 技能培训与其他相关类（6）
     */
    public void setSkillTrainScore(Double skillTrainScore) {
        this.skillTrainScore = skillTrainScore;
    }

    /**
     * 获取总得分
     *
     * @return total_score - 总得分
     */
    public Double getTotalScore() {
        return totalScore;
    }

    /**
     * 设置总得分
     *
     * @param totalScore 总得分
     */
    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}