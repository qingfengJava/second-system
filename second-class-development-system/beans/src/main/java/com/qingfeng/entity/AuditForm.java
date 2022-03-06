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
 * 初级审核表实体类
 *
 * @author 清风学Java
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "audit_form")
public class AuditForm {
    /**
     * 初级审核表主键Id
     */
    @Id
    @Column(name = "audit_form_id")
    private Integer auditFormId;

    /**
     * 审核人主键Id
     */
    @Column(name = "check_uid")
    private Integer checkUid;

    /**
     * 活动申请表主键id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 审核人的姓名
     */
    @Column(name = "check_username")
    private String checkUsername;

    /**
     * 审核人的职务
     */
    @Column(name = "check_user_duty")
    private String checkUserDuty;

    /**
     * 是否同意申请（0,：未同意  1：同意申请 2: 不同意申请）
     */
    @Column(name = "is_agree")
    private Integer isAgree;

    /**
     * 审批的内容
     */
    private String content;

    /**
     * 初级审批的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除（0：未删除    1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;
}