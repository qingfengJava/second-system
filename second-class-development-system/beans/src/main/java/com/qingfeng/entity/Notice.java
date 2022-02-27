package com.qingfeng.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Notice {
    /**
     * 社团公告主键Id
     */
    @Id
    @Column(name = "notice_id")
    private Integer noticeId;

    /**
     * 行政组织id
     */
    @Column(name = "organize_id")
    private Integer organizeId;

    /**
     * 公告主题
     */
    private String title;

    /**
     * 发布人姓名
     */
    @Column(name = "public_name")
    private String publicName;

    /**
     * 职位
     */
    private String duty;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 直接领导人
     */
    private String leader;

    /**
     * 直接领导人职位
     */
    @Column(name = "leader_post")
    private String leaderPost;

    /**
     * 发布时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除   0：未删除  1：已删除
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 获取社团公告主键Id
     *
     * @return notice_id - 社团公告主键Id
     */
    public Integer getNoticeId() {
        return noticeId;
    }

    /**
     * 设置社团公告主键Id
     *
     * @param noticeId 社团公告主键Id
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 获取行政组织id
     *
     * @return organize_id - 行政组织id
     */
    public Integer getOrganizeId() {
        return organizeId;
    }

    /**
     * 设置行政组织id
     *
     * @param organizeId 行政组织id
     */
    public void setOrganizeId(Integer organizeId) {
        this.organizeId = organizeId;
    }

    /**
     * 获取公告主题
     *
     * @return title - 公告主题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置公告主题
     *
     * @param title 公告主题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取发布人姓名
     *
     * @return public_name - 发布人姓名
     */
    public String getPublicName() {
        return publicName;
    }

    /**
     * 设置发布人姓名
     *
     * @param publicName 发布人姓名
     */
    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    /**
     * 获取职位
     *
     * @return duty - 职位
     */
    public String getDuty() {
        return duty;
    }

    /**
     * 设置职位
     *
     * @param duty 职位
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * 获取公告内容
     *
     * @return content - 公告内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置公告内容
     *
     * @param content 公告内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取直接领导人
     *
     * @return leader - 直接领导人
     */
    public String getLeader() {
        return leader;
    }

    /**
     * 设置直接领导人
     *
     * @param leader 直接领导人
     */
    public void setLeader(String leader) {
        this.leader = leader;
    }

    /**
     * 获取直接领导人职位
     *
     * @return leader_post - 直接领导人职位
     */
    public String getLeaderPost() {
        return leaderPost;
    }

    /**
     * 设置直接领导人职位
     *
     * @param leaderPost 直接领导人职位
     */
    public void setLeaderPost(String leaderPost) {
        this.leaderPost = leaderPost;
    }

    /**
     * 获取发布时间
     *
     * @return create_time - 发布时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发布时间
     *
     * @param createTime 发布时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否删除   0：未删除  1：已删除
     *
     * @return is_delete - 是否删除   0：未删除  1：已删除
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除   0：未删除  1：已删除
     *
     * @param isDelete 是否删除   0：未删除  1：已删除
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}