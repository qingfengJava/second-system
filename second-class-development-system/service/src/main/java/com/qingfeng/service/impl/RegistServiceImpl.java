package com.qingfeng.service.impl;

import com.qingfeng.dao.RegistMapper;
import com.qingfeng.entity.Regist;
import com.qingfeng.service.RegistService;
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
public class RegistServiceImpl implements RegistService {

    @Autowired
    private RegistMapper registMapper;

    @Override
    public ResultVO registrationActive(Integer applyId, Regist regist) {
        //设置参数
        regist.setApplyId(applyId);
        regist.setRegCreateTime(new Date());
        //要根据用户报名的角色判断用户报名是否暂时成功
        if ("参与者".equals(regist.getType())){
            //参与者直接报名成功
            regist.setIsSuccess(1);
            regist.setStatus(1);
            regist.setCheckImg("报名成功！");
        }else {
            //组织者报名未成功，身份等待管理员审核
            regist.setIsSuccess(0);
            regist.setStatus(0);
            regist.setCheckImg("参与者身份待审核！");
        }
        regist.setIsDelete(0);
        regist.setIsSign(0);

        //调用持久层，保存数据
        int i = registMapper.insertUseGeneratedKeys(regist);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"活动报名成功，请按时参与活动！！！",regist);
        }
        return new ResultVO(ResStatus.NO,"网络异常活动报名失败！！！",null);
    }

    /**
     * 要审核报名的，一定是审核报名者的参与者身份
     * @param activeRegId
     * @param status
     * @return
     */
    @Override
    public ResultVO checkIsSuccess(Integer activeRegId, Integer status) {
        //根据审核状态进行审核  2-审核身份通过   3-审核身份不通过
        if (status == 2){
            //身份正确  直接将信息填写到数据库
            Regist regist = new Regist();
            regist.setActiveRegId(activeRegId);
            regist.setIsSuccess(1);
            regist.setStatus(status);
            regist.setCheckImg("身份审核通过，报名成功！");
            //对数据库信息进行修改
            int i = registMapper.updateByPrimaryKeySelective(regist);
            if(i > 0){
                return new ResultVO(ResStatus.OK,"success",regist);
            }
        }else if (status == 3){
            //说明身份不正确，要进行修改
            Regist regist = new Regist();
            regist.setActiveRegId(activeRegId);
            regist.setType(1);
            regist.setIsSuccess(1);
            regist.setStatus(status);
            regist.setCheckImg("报名成功，身份审核不通过，系统已经自动修改！");
            int i = registMapper.updateByPrimaryKeySelective(regist);
            if (i > 0){
                //修改成功
                return new ResultVO(ResStatus.OK,"success",regist);
            }
        }
        return new ResultVO(ResStatus.NO,"fail","网络异常，请稍后再试！");
    }

    @Override
    public ResultVO deleteRegistration(Integer activeRegId) {
        Regist regist = new Regist();
        regist.setActiveRegId(activeRegId);
        regist.setIsDelete(1);
        int i = registMapper.updateByPrimaryKeySelective(regist);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"删除报名信息成功！",null);
        }
        return new ResultVO(ResStatus.NO,"网络异常，删除失败！",null);
    }

    @Override
    public ResultVO updateRegistration(Integer activeRegId, Regist regist) {
        //将主键Id设置到报名对象中
        regist.setActiveRegId(activeRegId);
        int i = registMapper.updateByPrimaryKeySelective(regist);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"修改报名信息成功！",regist);
        }
        return new ResultVO(ResStatus.NO,"网络异常，修改信息报名失败！",null);
    }
}
