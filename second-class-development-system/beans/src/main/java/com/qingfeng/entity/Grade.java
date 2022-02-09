package com.qingfeng.entity;

import java.util.Date;
import javax.persistence.*;

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
     * 评价级别（星级）
     */
    @Column(name = "grade_level")
    private Integer gradeLevel;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取社团评级表Id
     *
     * @return grade_id - 社团评级表Id
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * 设置社团评级表Id
     *
     * @param gradeId 社团评级表Id
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * 获取社团Id
     *
     * @return user_id - 社团Id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置社团Id
     *
     * @param userId 社团Id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取评价级别（星级）
     *
     * @return grade_level - 评价级别（星级）
     */
    public Integer getGradeLevel() {
        return gradeLevel;
    }

    /**
     * 设置评价级别（星级）
     *
     * @param gradeLevel 评价级别（星级）
     */
    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}