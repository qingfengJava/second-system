package com.qingfeng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户公告实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user_notice")
public class UserNotice {
    /**
     * 用户公告表主键Id
     */
    @Id
    @Column(name = "user_notice_id")
    private Integer userNoticeId;

    /**
     * 用户Id
     */
    private Integer uid;

    /**
     * 公告表Id
     */
    @Column(name = "notice_id")
    private Integer noticeId;

    /**
     * 是否查看（0：未查看   1： 已查看）
     */
    @Column(name = "is_check")
    private Integer isCheck;

    /**
     * 创建时间（首次查看时间）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间（最后一次查看时间）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;
}