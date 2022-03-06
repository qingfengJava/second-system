package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.AuditForm;
import com.qingfeng.entity.Users;
import com.qingfeng.service.EmailService;
import com.qingfeng.service.UserService;
import com.qingfeng.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/6
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ApplyMapper applyMapper;

    /**
     * 发送需要审核的邮箱信息
     * @param isAdmin
     * @param apply
     */
    @Override
    public void sendNeedToCheckEmail(int isAdmin, Apply apply) {
        //查询审核人的用户的邮箱信息，如果邮箱存在，那么就发送邮件提醒审核
        List<Users> userList = userService.selectByUserIdentity(3);
        if (userList != null && userList.size() > 0){
            //说明有用户信息  一般来说学生社团联只有一个公用账号 但是这里还是遍历查询
            for (Users users : userList) {
                if (!"".equals(users.getEmail()) || users.getEmail() != null){
                    //邮箱存在，可以发送邮件  封装要发送的信息进行消息的发送
                    String msg = "有新活动申请，请尽快前往官网审核申请信息！\r\n"+
                            "申请组织："+apply.getHostName()+"\r\n"+
                            "活动名称："+apply.getActiveName()+"\r\n"+
                            "活动基本内容："+apply.getActiveContent();
                    emailUtils.sendEmail(users.getEmail(),"活动申请审核",msg);
                }
            }
        }
    }

    /**
     * 给申请人发送的邮箱信息
     * @param applyId
     * @param auditForm
     */
    @Override
    public void sendCheckAgreeEmail(Integer applyId, AuditForm auditForm) {
        //查询需要接收者的用户信息，如果邮箱存在就发送，如果邮箱不存在那么就不发送
        //先根据applyId查询申请人的用户Id
        Apply apply = applyMapper.selectByPrimaryKey(applyId);
        //根据apply拿到用户Id
        if (apply != null && apply.getApplyUserId() != null){
            Users users = usersMapper.selectByPrimaryKey(apply.getApplyUserId());
            if (users != null && (users.getEmail() != null || !"".equals(users.getEmail()))){
                //说明邮箱存在，可以发送邮件
                String msg = "活动申请审核通过，请按时开展活动：\r\n"+
                        "审核人："+auditForm.getCheckUsername()+"\r\n"+
                        "职务："+auditForm.getCheckUserDuty()+"\r\n"+
                        "审核时间："+auditForm.getCreateTime();
                emailUtils.sendEmail(users.getEmail(),"活动审核结果",msg);
            }
        }
    }

    /**
     * 审核不通过的邮箱信息
     * @param applyId
     * @param auditForm
     */
    @Override
    public void sendCheckNeedToUpdate(Integer applyId, AuditForm auditForm) {
        //查询需要接收者的用户信息，如果邮箱存在就发送，如果邮箱不存在那么就不发送
        //先根据applyId查询申请人的用户Id
        Apply apply = applyMapper.selectByPrimaryKey(applyId);
        //根据apply拿到用户Id
        if (apply != null && apply.getApplyUserId() != null){
            Users users = usersMapper.selectByPrimaryKey(apply.getApplyUserId());
            if (users != null && (users.getEmail() != null || !"".equals(users.getEmail()))){
                //说明邮箱存在，可以发送邮件
                String msg = "活动申请审核不通过，请及时修改活动申请：\r\n"+
                        "审核人："+auditForm.getCheckUsername()+"\r\n"+
                        "职务："+auditForm.getCheckUserDuty()+"\r\n"+
                        "审核内容："+auditForm.getContent()+"\r\n"+
                        "审核时间："+auditForm.getCreateTime();
                emailUtils.sendEmail(users.getEmail(),"活动审核结果",msg);
            }
        }
    }
}
