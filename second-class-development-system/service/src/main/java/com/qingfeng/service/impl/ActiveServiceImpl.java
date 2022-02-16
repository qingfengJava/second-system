package com.qingfeng.service.impl;

import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.RegistMapper;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.Regist;
import com.qingfeng.entity.RegistVo;
import com.qingfeng.service.ActiveService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/14
 */
@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private RegistMapper registMapper;

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
    public ResultVO registrationActive(Integer applyId,Regist regist) {
        //设置参数
        regist.setApplyId(applyId);
        regist.setRegCreateTime(new Date());
        //要根据用户报名的角色判断用户报名是否暂时成功
        if ("参与者".equals(regist.getType())){
            //参与者直接报名成功
            regist.setIsSuccess(1);
        }else {
            //组织者等待管理员审核
            regist.setIsSuccess(0);
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

    @Override
    public ResultVO checkRegistration(String uid,int pageNum,int limit) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            //根据用户id调用持久层分页查询学生报名待参与的活动
            List<RegistVo> registVoList = registMapper.checkRegistration(Integer.parseInt(uid),start,limit);

            //查询总记录数
            Example example = new Example(Regist.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("userId",uid);
            criteria.andEqualTo("isSign",0);
            int count = registMapper.selectCountByExample(example);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<RegistVo> pageHelper = new PageHelper<>(count, pageCount, registVoList);

            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Override
    public ResultVO queryApply(int pageNum, int limit) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            List<Apply> applyList = applyMapper.queryApply(start,limit);
            //查询总记录数
            Example example = new Example(Apply.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isEnd",0);
            int count = applyMapper.selectCountByExample(example);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<Apply> pageHelper = new PageHelper<>(count, pageCount, applyList);

            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

}
