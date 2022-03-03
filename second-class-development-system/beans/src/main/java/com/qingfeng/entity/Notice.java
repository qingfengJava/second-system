package com.qingfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 公告实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @Column(name = "user_id")
    private Integer userId;

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
     * 发布对象
     */
    @Column(name = "release_object")
    private Integer releaseObject;

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
    private Integer isDelete;
}