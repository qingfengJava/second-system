package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.constant.UserStatus;
import com.qingfeng.dao.SystemFeedbackMapper;
import com.qingfeng.entity.SystemFeedback;
import com.qingfeng.service.SystemFeedbackService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.ResultVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/14
 */
@Service
public class SystemFeedbackServiceImpl implements SystemFeedbackService {

    @Autowired
    private SystemFeedbackMapper systemFeedbackMapper;

    @CacheEvict(value = "systemFeedback", allEntries=true)
    @Override
    public ResultVO addFeedback(Integer uid, SystemFeedback systemFeedback) {
        systemFeedback.setUid(uid);
        systemFeedback.setCreateTime(new Date());
        systemFeedback.setIsReceive(0);
        systemFeedback.setIsDelete(0);
        systemFeedback.setReceiveContent("");
        int i = systemFeedbackMapper.insertUseGeneratedKeys(systemFeedback);
        return new ResultVO(ResStatus.OK,"success",null);
    }

    /**
     * 分页条件查询反馈信息列表：要注意：条件判断
     * @param uid
     * @param isAdmin
     * @param pageNum
     * @param limit
     * @param isReceive
     * @param feedbackType
     * @return
     */
    @Cacheable(value = "systemFeedback",keyGenerator = "keyGenerator")
    @Override
    public PageHelper<SystemFeedback> queryFeedbacks(Integer uid, Integer isAdmin, Integer pageNum, Integer limit, Integer isReceive,Integer feedbackType) {
        int start = (pageNum - 1) * limit;
        RowBounds rowBounds = new RowBounds(start, limit);
        //封装查询条件
        Example example = new Example(SystemFeedback.class);
        Example.Criteria criteria = example.createCriteria();
        if(isAdmin != UserStatus.LEADER_CONSTANTS && isAdmin != UserStatus.ADMIN_CONSTANTS){
            //只有校领导和管理员才可以查看全部的反馈信息
            criteria.andEqualTo("uid",uid);
        }
        if(isReceive != null && isReceive != -1){
            //反馈信息的状态
            criteria.andEqualTo("isReceive",isReceive);
        }
        if (feedbackType != null &&feedbackType != 0){
            //反馈对象的类型
            criteria.andEqualTo("feedbackType",feedbackType);
        }
        criteria.andEqualTo("isDelete",0);
        List<SystemFeedback> systemFeedbackList = systemFeedbackMapper.selectByExampleAndRowBounds(example, rowBounds);
        int count = systemFeedbackMapper.selectCountByExample(example);
        //计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        PageHelper<SystemFeedback> pageHelper = new PageHelper<>(count, pageCount, systemFeedbackList);
        return pageHelper;
    }

    @CacheEvict(value = "systemFeedback", allEntries=true)
    @Override
    public ResultVO deleteFeedback(Integer[] feedbacks) {
        SystemFeedback systemFeedback = new SystemFeedback();
        systemFeedback.setIsDelete(1);
        Example example = new Example(SystemFeedback.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("systemId", Arrays.asList(feedbacks));
        int i = systemFeedbackMapper.updateByExampleSelective(systemFeedback, example);
        if (i > 0){
            return new ResultVO(ResStatus.OK,"success",null);
        }
        return new ResultVO(ResStatus.NO,"网络异常，删除失败！",null);
    }
}
