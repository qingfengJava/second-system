package com.qingfeng.sms.service.impl;

import com.qingfeng.sms.entity.EmailEntity;
import com.qingfeng.sms.service.EmailService;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件信息
     * @param emailEntity
     * @return
     */
    @Override
    public Integer sendEmail(EmailEntity emailEntity) {
        // 目前先这样发，后面整合消息队列，使用消息队列进行发送
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("攀枝花学院第二课堂素质拓展学分管理系统 <2562777581@qq.com>");
        message.setSentDate(new Date());
        message.setTo(emailEntity.getEmail());
        // 标题
        message.setSubject(emailEntity.getTitle());
        // 内容
        message.setText(emailEntity.getBody());
        try {
            javaMailSender.send(message);
            return 1;
        } catch (MailSendException e) {
            return 2;
        } catch (Exception e) {
            return 0;
        }
    }
}
