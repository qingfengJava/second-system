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

    @Override
    public ResultVO queryApplyDetails(String applyId) {
        Example example = new Example(Apply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyId",applyId);
        List<Apply> applyList = applyMapper.selectByExample(example);
        if (applyList.size() > 0){
            return new ResultVO(ResStatus.OK,"success",applyList.get(0));
        }else {
            return new ResultVO(ResStatus.NO,"网络异常，信息不存在！",null);
        }
    }

}
