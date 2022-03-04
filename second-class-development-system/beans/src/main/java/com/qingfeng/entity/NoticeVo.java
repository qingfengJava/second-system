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
 * 公告实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeVo {
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除   0：未删除  1：已删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 公告表实体维护用户实体
     */
    private UsersVo usersVo;
}