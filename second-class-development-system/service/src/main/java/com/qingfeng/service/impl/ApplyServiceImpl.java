package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.service.ActiveService;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/14
 */
@Service
public class ApplyServiceImpl implements ActiveService {

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
}
