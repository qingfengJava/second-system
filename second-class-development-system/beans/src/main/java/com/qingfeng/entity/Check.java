package com.qingfeng.entity;

import java.util.Date;
import javax.persistence.*;

public class Check {
    /**
     * 审核表主键Id
     */
    @Id
    @Column(name = "checkId")
    private Integer checkid;

    /**
     * 审核组织id 
     */
    @Column(name = "check_user_id")
    private Integer checkUserId;

    /**
     * 活动id
     */
    @Column(name = "activeId")
    private Integer activeid;

    /**
     * 活动申请人的Id
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 活动举办的图片
     */
    @Column(name = "active_img")
    private String activeImg;

    /**
     * 活动总结
     */
    @Column(name = "active_summary")
    private String activeSummary;

    /**
     * 审核时间
     */
    @Column(name = "check_time")
    private Date checkTime;

    /**
     * 是否通过(0: 审核不通过  1：通过)
     */
    @Column(name = "is_pass")
    private Integer isPass;

    /**
     * 是否删除（0：未删除  1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 获取审核表主键Id
     *
     * @return checkId - 审核表主键Id
     */
    public Integer getCheckid() {
        return checkid;
    }

    /**
     * 设置审核表主键Id
     *
     * @param checkid 审核表主键Id
     */
    public void setCheckid(Integer checkid) {
        this.checkid = checkid;
    }

    /**
     * 获取审核组织id 
     *
     * @return check_user_id - 审核组织id 
     */
    public Integer getCheckUserId() {
        return checkUserId;
    }

    /**
     * 设置审核组织id 
     *
     * @param checkUserId 审核组织id 
     */
    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    /**
     * 获取活动id
     *
     * @return activeId - 活动id
     */
    public Integer getActiveid() {
        return activeid;
    }

    /**
     * 设置活动id
     *
     * @param activeid 活动id
     */
    public void setActiveid(Integer activeid) {
        this.activeid = activeid;
    }

    /**
     * 获取活动申请人的Id
     *
     * @return userId - 活动申请人的Id
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置活动申请人的Id
     *
     * @param userid 活动申请人的Id
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取活动举办的图片
     *
     * @return active_img - 活动举办的图片
     */
    public String getActiveImg() {
        return activeImg;
    }

    /**
     * 设置活动举办的图片
     *
     * @param activeImg 活动举办的图片
     */
    public void setActiveImg(String activeImg) {
        this.activeImg = activeImg;
    }

    /**
     * 获取活动总结
     *
     * @return active_summary - 活动总结
     */
    public String getActiveSummary() {
        return activeSummary;
    }

    /**
     * 设置活动总结
     *
     * @param activeSummary 活动总结
     */
    public void setActiveSummary(String activeSummary) {
        this.activeSummary = activeSummary;
    }

    /**
     * 获取审核时间
     *
     * @return check_time - 审核时间
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核时间
     *
     * @param checkTime 审核时间
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取是否通过(0: 审核不通过  1：通过)
     *
     * @return is_pass - 是否通过(0: 审核不通过  1：通过)
     */
    public Integer getIsPass() {
        return isPass;
    }

    /**
     * 设置是否通过(0: 审核不通过  1：通过)
     *
     * @param isPass 是否通过(0: 审核不通过  1：通过)
     */
    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    /**
     * 获取是否删除（0：未删除  1：已删除）
     *
     * @return is_delete - 是否删除（0：未删除  1：已删除）
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（0：未删除  1：已删除）
     *
     * @param isDelete 是否删除（0：未删除  1：已删除）
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}