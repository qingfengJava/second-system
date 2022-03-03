package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.service.ApplyService;
import com.qingfeng.utils.CompareDateUtils;
import com.qingfeng.utils.DateFormatUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultVO applyActive(Apply apply) {
        //首先要检查活动报名时间和活动开展时间是否符合逻辑规范（活动报名时间不能超过活动开展时间）
        //活动开始的时间
        String strDate = apply.getActiveTime().substring(0, apply.getActiveTime().indexOf("~") - 1);
        System.out.println(strDate);
        //活动开始的日期
        Date date1 = DateFormatUtils.strToDate(strDate, "yyyy-MM-dd");
        //活动报名截止的日期
        Date date2 = apply.getRegistrationDeadline();

        //首先审查活动申请时间，活动申请时间比活动开始时间应该最少提前一周进行申请
        //使用默认时区和语言环境获得一个日历。
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        //取当前日期的前一周
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date time = cal.getTime();
        //只要当前日期小于活动开始日期减1周的时间就行  返回的是false
        if (!CompareDateUtils.compareDate(new Date(), time)){
            //说明活动申请的时间没有问题
            //进行日期的比较（活动报名日期不大于活动开始的日期） 比较返回值也是false
            if(!CompareDateUtils.compareDate(date2, date1)){
                //说明活动截止日期，没有问题
                //给对象设置一些必填值
                apply.setApplyCreateTime(new Date());
                apply.setIsAgree(0);
                apply.setIsDelete(0);
                int i = applyMapper.insertUseGeneratedKeys(apply);
                if (i > 0){
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

    @Override
    public ResultVO checkApplyActive(Integer applyId,Integer isAgree) {
        Apply apply = new Apply();
        apply.setApplyId(applyId);
        apply.setIsAgree(isAgree);
        int i = applyMapper.updateByPrimaryKeySelective(apply);
        if (i > 0){
            if (isAgree == 1){
                return new ResultVO(ResStatus.OK,"审核通过！",null);
            }else if (isAgree == 2){
                return new ResultVO(ResStatus.OK,"审核不通过！",null);
            }
        }
        return new ResultVO(ResStatus.NO,"网络异常，审核失败！",null);
    }

    @Override
    public ResultVO deleteApplyActive(Integer applyId) {
        Apply apply = new Apply();
        apply.setApplyId(applyId);
        apply.setIsDelete(1);
        int i = applyMapper.updateByPrimaryKeySelective(apply);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"删除活动申请成功！",null);
        }
        return new ResultVO(ResStatus.NO,"网络异常，删除活动申请失败！",null);
    }

    @Override
    public ResultVO updateApplyActive(Integer applyId, Apply apply) {
        //将申请表的主键Id设置到apply中
        apply.setApplyId(applyId);
        int i = applyMapper.updateByPrimaryKeySelective(apply);
        if (i > 0){
            //i>0 说明信息修改成功
            return new ResultVO(ResStatus.OK,"修改活动申请信息成功！",apply);
        }
        return new ResultVO(ResStatus.NO,"网络异常，修改信息申请信息失败！",null);
    }
}
