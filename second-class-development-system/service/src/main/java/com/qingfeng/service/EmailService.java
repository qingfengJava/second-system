package com.qingfeng.service;

import com.qingfeng.entity.Apply;
import com.qingfeng.entity.AuditForm;
import com.qingfeng.entity.Regist;

/**
 * 发送邮件的业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/6
 */
public interface EmailService {

    /**
     * 向社团联审核人发送审核邮箱信息
     * @param isAdmin
     * @param apply
     */
    void sendNeedToCheckEmail(int isAdmin, Apply apply);

    /**
     * 向社团发送审核通过的邮箱信息
     * @param applyId
     * @param auditForm
     */
    void sendCheckAgreeEmail(Integer applyId, AuditForm auditForm);

    /**
     * 向社团发送审核不通过的邮箱信息
     * @param applyId
     * @param auditForm
     */
    void sendCheckNeedToUpdate(Integer applyId, AuditForm auditForm);

    /**
     * 给用户活动报名身份审核通过的用户发送邮件提醒参加活动
     * @param activeRegId
     */
    void sendCheckSuccess(Integer activeRegId);

    /**
     * 给活动申请人发送审核用户申请的信息
     * @param regist
     */
    void sendCheckType(Regist regist);
}
