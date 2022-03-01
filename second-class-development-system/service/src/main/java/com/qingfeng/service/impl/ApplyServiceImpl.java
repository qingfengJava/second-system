package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.service.ApplyService;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
