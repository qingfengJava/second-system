package com.qingfeng.utils;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/6
 */
@Component
public class EmailUtils {

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送邮件的封装类
     * @param email  要发送的邮件的账号
     * @param title  邮件的标题
     * @param body   邮件的内容
     * @return
     */
    public int sendEmail(String email, String title, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("攀枝花学院第二课堂管理中心 <2562777581@qq.com>");
        message.setSentDate(new Date());
        message.setTo(email);
        // 标题
        message.setSubject(title);
        // 内容
        message.setText(body);
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
