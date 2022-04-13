package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.RegistMapper;
import com.qingfeng.dao.UsersMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.Regist;
import com.qingfeng.entity.Users;
import com.qingfeng.service.EmailService;
import com.qingfeng.service.RegistService;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    @Transactional
    public ResultVO registrationActive(Integer applyId, Regist regist) {
        //学生报名  首先检查学生是否已经报名过该活动
        Example example = new Example(Regist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyId", applyId);
        criteria.andEqualTo("userId", regist.getUserId());
        Regist oldRegist = registMapper.selectOneByExample(example);
        if (oldRegist == null) {
            //说明没有报名过该活动，可以报名   检查学生学号和姓名是不是本人
            Example example1 = new Example(Users.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("uid", regist.getUserId());
            criteria1.andEqualTo("username", regist.getStudentId());
            //保证学号正确
            Users users = usersMapper.selectOneByExample(example1);
            if (users != null) {
                //主键唯一   学号唯一  用户不为null  说明正确
                //设置参数
                regist.setApplyId(applyId);
                regist.setRegCreateTime(new Date());
                //要根据用户报名的角色判断用户报名是否暂时成功
                if (regist.getType() == 0) {
                    //参与者直接报名成功
                    regist.setIsSuccess(1);
                    regist.setStatus(1);
                    regist.setCheckMsg("报名成功！");
                } else {
                    //组织者报名未成功，身份等待管理员审核
                    regist.setIsSuccess(0);
                    regist.setStatus(0);
                    regist.setCheckMsg("参与者身份待审核！");
                }
                regist.setIsDelete(0);
                regist.setIsSign(0);

                //调用持久层，保存数据
                int i = registMapper.insertUseGeneratedKeys(regist);
                if (i > 0) {
                    if (regist.getType() == 1){
                        //通知负责人审核信息
                        emailService.sendCheckType(regist);
                    }

                    //修改活动申请表的报名人数
                    Apply apply = applyMapper.selectByPrimaryKey(applyId);
                    apply.setTotalNum(apply.getTotalNum() + 1);
                    //进行修改
                    int updateResult = applyMapper.updateByPrimaryKeySelective(apply);
                    if (updateResult > 0) {
                        return new ResultVO(ResStatus.OK, "活动报名成功，请按时参与活动！！！", regist);
                    }
                }
                return new ResultVO(ResStatus.NO, "网络异常活动报名失败！！！", null);
            }else {
                return new ResultVO(ResStatus.NO, "请检查个人信息填写是否正确！", null);
            }
        }else {
            return new ResultVO(ResStatus.NO, "已报名过该活动！", null);
        }
    }

    /**
     * 要审核报名的，一定是审核报名者的参与者身份
     *
     * @param activeRegId
     * @param status
     * @return
     */
    @Override
    public ResultVO checkIsSuccess(Integer activeRegId, Integer status) {
        //根据审核状态进行审核  2-审核身份通过   3-审核身份不通过
        Regist regist = new Regist();
        regist.setActiveRegId(activeRegId);
        regist.setIsSuccess(1);
        regist.setStatus(status);
        if (status == 2) {
            //身份正确  直接将信息填写到数据库
            regist.setCheckMsg("身份审核通过，报名成功！");
        } else if (status == 3) {
            //说明身份不正确，要进行修改
            regist.setType(0);
            regist.setCheckMsg("报名成功，身份审核不通过，系统已经自动修改！");
        }
        //对数据库信息进行修改
        int i = registMapper.updateRegistById(regist);
        if (i > 0) {
            //修改成功  给对应的用户发送邮件，提醒用户及时参加活动
            emailService.sendCheckSuccess(activeRegId);
            return new ResultVO(ResStatus.OK, "success", regist);
        }
        return new ResultVO(ResStatus.NO, "fail", "网络异常，请稍后再试！");
    }

    @Override
    public ResultVO deleteRegistration(Integer activeRegId) {
        Regist regist = registMapper.selectByPrimaryKey(activeRegId);
        Regist newRegist = new Regist();
        newRegist.setIsDelete(1);
        Example example = new Example(Regist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("activeRegId",activeRegId);
        int i = registMapper.updateByExampleSelective(newRegist,example);
        if (i > 0) {
            //还要修改活动报名的人数
            Apply apply = applyMapper.selectByPrimaryKey(regist.getApplyId());
            Apply newApply = new Apply();
            newApply .setTotalNum(apply.getTotalNum() - 1 == 0 ? 0:apply.getTotalNum() - 1);
            //封装条件进行修改
            Example example1 = new Example(Apply.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("applyId",apply.getApplyId());
            int result = applyMapper.updateByExample(newApply, example1);
            if(result > 0){
                return new ResultVO(ResStatus.OK, "删除报名信息成功！", null);
            }
        }
        return new ResultVO(ResStatus.NO, "网络异常，删除失败！", null);
    }

    @Override
    public ResultVO updateRegistration(Integer activeRegId, Regist regist) {
        //修改信息也要注意审核学生修改的信息是否正确
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", regist.getUserId());
        criteria.andEqualTo("username", regist.getStudentId());
        //保证学号正确
        Users users = usersMapper.selectOneByExample(example);
        if (users != null) {
            //将主键Id设置到报名对象中
            regist.setActiveRegId(activeRegId);
            //基本信息没有问题，要检查用户修改的信息  要检查用户修改参与者身份没有，如果修改了参与者身份要重新审核
            if (regist.getType() == 1 && regist.getStatus() == 1) {
                //说明一定是修改过用户身份的
                regist.setStatus(0);
                regist.setIsSuccess(0);
                regist.setCheckMsg("用户参与身份待审核！");
            }
            if(regist.getType() == 0){
                regist.setStatus(1);
                regist.setIsSuccess(1);
                regist.setCheckMsg("报名成功！");
            }
            int i = registMapper.updateByPrimaryKeySelective(regist);
            if (i > 0) {
                if (regist.getType() == 1){
                    //通知负责人审核信息
                    emailService.sendCheckType(regist);
                    return new ResultVO(ResStatus.OK, "修改报名信息成功，参与者身份待审核！", regist);
                }
                return new ResultVO(ResStatus.OK, "修改报名信息成功！", regist);
            }
            return new ResultVO(ResStatus.NO, "网络异常，修改信息报名失败！", null);
        } else {
            return new ResultVO(ResStatus.NO, "请检查个人信息填写是否正确！", null);
        }
    }
}
