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
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "info_maintain")
public class InfoMaintain {
    /**
     * 信息维护表主键
     */
    @Id
    private Integer id;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 开始维护时间
     */
    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束维护时间
     */
    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    /**
     * 对象维护类型（1：学生   2：校领导/教师）
     */
    @Column(name = "object_type")
    private Integer objectType;

    /**
     * 是否结束（0： 未结束    1:已结束）
     */
    @Column(name = "is_end")
    private Integer isEnd;

}