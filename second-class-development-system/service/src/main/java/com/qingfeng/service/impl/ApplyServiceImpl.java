package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.AuditFormMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.AuditForm;
import com.qingfeng.service.ApplyService;
import com.qingfeng.service.EmailService;
import com.qingfeng.utils.CompareDateUtils;
import com.qingfeng.utils.DateFormatUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import com.qingfeng.vo.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/26
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private AuditFormMapper auditFormMapper;
    @Autowired
    private EmailService emailService;

    @Override
    public ResultVO applyActive(Integer uid, Apply apply) {
        //首先要检查活动报名时间和活动开展时间是否符合逻辑规范（活动报名时间不能超过活动开展时间）
        //活动开始的时间
        String strDate = apply.getActiveTime().substring(0, apply.getActiveTime().indexOf("~") - 1);
        //活动开始的日期
        Date startDate = DateFormatUtils.strToDate(strDate, "yyyy-MM-dd");
        //活动报名截止的日期
        Date endDate = apply.getRegistrationDeadline();

        //首先审查活动申请时间，活动申请时间比活动开始时间应该最少提前一周进行申请
        //使用默认时区和语言环境获得一个日历。
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        //取当前日期的前一周
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date time = cal.getTime();
        //只要当前日期小于活动开始日期减1周的时间就行  返回的是false
        if (!CompareDateUtils.compareDate(new Date(), time)){
            //说明活动申请的时间没有问题
            //进行日期的比较（活动报名日期不大于活动开始的日期） 比较返回值也是false
            if(!CompareDateUtils.compareDate(endDate, startDate)){
                //说明活动截止日期，没有问题
                //给对象设置一些必填值
                apply.setApplyUserId(uid);
                apply.setApplyCreateTime(new Date());
                apply.setIsAgree(0);
                apply.setIsDelete(0);
                int i = applyMapper.insertUseGeneratedKeys(apply);
                if (i > 0){
                    //活动申请成功，发送邮件通知负责人审核
                    emailService.sendNeedToCheckEmail(UserStatus.STUDENTS_UNION_CONSTANTS,apply);

                    return new ResultVO(ResStatus.OK,"活动申请成功，尽快通知负责人审核！",apply);
                }else {
                    return new ResultVO(ResStatus.NO,"服务器异常活动申请失败！！！",null);
                }
            }else{
                return new ResultVO(ResStatus.NO,"活动报名截止日期不能长于活动开始时间！",null);
            }
        }else{
            return new ResultVO(ResStatus.NO,"活动申请时间必须比活动开始时间提前一周以上才行！",null);
        }
    }

    /**
     * 活动申请审核：要一并将初级审核表信息一起记录
     *
     * @param applyId
     * @param auditForm  初级审核表实体类，封装审核的信息
     * @return
     */
    @Transactional
    @Override
    public ResultVO checkApplyActive(Integer applyId, AuditForm auditForm) {
        //先修改Apply表
        int i = applyMapper.updateByApplyId(applyId, auditForm.getIsAgree());
        if (i > 0){
            //修改完Apply表又修改初级审核表  先查询初级审核表中是否有记录 有记录是修改，没记录是新增
            Example example = new Example(AuditForm.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("applyId",applyId);
            AuditForm oldAuditForm = auditFormMapper.selectOneByExample(example);
            //定义一个变量用于记录结果
            int result = 0;
            auditForm.setCreateTime(new Date());
            auditForm.setIsDelete(0);
            if (oldAuditForm == null){
                //说明是新增   封装一些基本的数据
                auditForm.setApplyId(applyId);
                //新增审核数据
                result = auditFormMapper.insertUseGeneratedKeys(auditForm);
            }else {
                //说明有记录是修改审核结果
                auditForm.setAuditFormId(oldAuditForm.getAuditFormId());
                //修改数据
                result = auditFormMapper.updateByPrimaryKeySelective(auditForm);
            }

            if (result > 0){
                if (auditForm.getIsAgree() == 1){
                    //审核后的活动需要发送对应的邮件给对用的社团负责人，让其及时关注
                    emailService.sendCheckAgreeEmail(applyId,auditForm);
                    return new ResultVO(ResStatus.OK,"审核通过！",null);
                }else if (auditForm.getIsAgree() == 2){
                    //审核不通过，要及时通知社团负责人修改活动申请
                    emailService.sendCheckNeedToUpdate(applyId,auditForm);
                    return new ResultVO(ResStatus.OK,"审核不通过！",null);
                }
            }
        }
        return new ResultVO(ResStatus.NO,"网络异常，审核失败！",null);
    }

    /**
     * 删除活动要注意：
     *      1、将申请表的isDelete值修改为1
     *      2、将初级审核表的isDelete值修改为1
     * @param applyId
     * @return
     */
    @Override
    public ResultVO deleteApplyActive(Integer applyId) {
        //先修改申请表的值
        int i = applyMapper.deleteByApplyId(applyId);
        if (i > 0){
            //说明申请表信息删除成功  再删除初级审核表中关联的信息
            int k = auditFormMapper.deleteByApplyId(applyId);
            if (k > 0){
                //说明删除活动申请成功
                return new ResultVO(ResStatus.OK,"删除活动申请成功！",null);
            }
        }
        return new ResultVO(ResStatus.NO,"网络异常，删除活动申请失败！",null);
    }

    /**
     * 修改活动申请的注意事项：
     *      1、必须是活动未开始之前。
     *      2、同时修改活动之后要重新经过审核才行。
     *      3、修改活动信息要对应修改初级审核表中的审核信息
     * @param applyId
     * @param apply
     * @return
     */
    @Transactional
    @Override
    public ResultVO updateApplyActive(Integer applyId, Apply apply) {
        //做逻辑判断，虽然前端一般会控制，但是后台依然控制一下   先查询原来的实体对象
        Apply oldApply = applyMapper.selectByPrimaryKey(applyId);
        //判断活动是否已经开始
        String subTime = oldApply.getActiveTime().substring(0, oldApply.getActiveTime().indexOf("~") - 1);
        //活动开始的日期
        Date startDate = DateFormatUtils.strToDate(subTime, "yyyy-MM-dd");
        //修改活动申请的时间至少应该在活动开始前两天进行修改
        //使用默认时区和语言环境获得一个日历。
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        //取当前日期的前一周
        cal.add(Calendar.DAY_OF_MONTH, -2);
        Date endTime = cal.getTime();
        //只要当前时间不大于活动开始的时间减两天就行
        if(!CompareDateUtils.compareDate(new Date(), endTime)){
            //要判断当前活动是否结束
            if (oldApply.getIsEnd() == 0){
                //说明没有结束，接下来验证修改的信息，主要验证活动举办的日期是否正确
                String strTime = apply.getActiveTime().substring(0, apply.getActiveTime().indexOf("~") - 1);
                //新活动开始的日期
                Date dateStart = DateFormatUtils.strToDate(strTime, "yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                //新修改活动申请的开始时间至少应该是当前时间提前两天
                calendar.setTime(dateStart);
                //取当前日期的前一周
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                Date dateEnd = calendar.getTime();
                //只要当前时间不大于活动开始时间减两天的时间就行
                if(!CompareDateUtils.compareDate(new Date(), dateEnd)){
                    //将申请表的主键Id设置到apply中
                    apply.setApplyId(applyId);
                    apply.setApplyUserId(oldApply.getApplyUserId());
                    //不管之前有没有同意申请，都要修改为不同意申请
                    apply.setIsAgree(0);
                    apply.setIsEnd(0);
                    apply.setIsDelete(0);
                    int i = applyMapper.updateByPrimaryKeySelective(apply);
                    if (i > 0){
                        //i>0 说明申请表信息修改成功  将关联的初级审核表的信息一并修改，主要就是删除之前的审核信息
                        //先查询就的审核信息，如果没有就不用修改了
                        Example example = new Example(AuditForm.class);
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andEqualTo("applyId",applyId);
                        AuditForm auditForm = auditFormMapper.selectOneByExample(example);
                        if (auditForm != null){
                            //直接进行修改即可
                            auditForm.setIsAgree(0);
                            auditForm.setContent("");
                            auditForm.setIsDelete(0);
                            int result = auditFormMapper.updateByPrimaryKeySelective(auditForm);
                            if(result <= 0){
                                throw new RuntimeException();
                            }
                        }
                        //活动信息修改成功，要通知负责人审核
                        emailService.sendNeedToCheckEmail(UserStatus.STUDENTS_UNION_CONSTANTS,apply);

                        return new ResultVO(ResStatus.OK,"修改活动申请信息成功！",apply);
                    }
                    return new ResultVO(ResStatus.NO,"网络异常，修改申请信息失败！",null);
                }else{
                    return new ResultVO(ResStatus.NO,"活动开始时间距离当前时间不足两天，不允许修改！",null);
                }
            }else{
                return new ResultVO(ResStatus.NO,"活动已经结束，无法修改活动申请！",null);
            }
        }else{
            return new ResultVO(ResStatus.NO,"距离活动开始还有不足两天的时间，无法修改活动申请！",null);
        }
    }
}
