package com.qingfeng.service.impl;

import com.qingfeng.dao.NoticeMapper;
import com.qingfeng.dao.UserNoticeMapper;
import com.qingfeng.entity.Notice;
import com.qingfeng.entity.NoticeVo;
import com.qingfeng.entity.UserNotice;
import com.qingfeng.service.NoticeService;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/4
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private UserNoticeMapper userNoticeMapper;

    @Override
    public ResultVO addNotice(Integer userId, Notice notice) {
        //设置基本信息
        notice.setUserId(userId);
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        notice.setIsDelete(0);
        //使用主键进行添加数据并实现主键回填
        int i = noticeMapper.insertUseGeneratedKeys(notice);
        if (i > 0){
            //说明插入成功
            return new ResultVO(ResStatus.OK,"发布成功！！！",notice);
        }
        return new ResultVO(ResStatus.NO,"网络异常，公告发布失败！！！",null);
    }

    @Override
    @Transactional
    public ResultVO updateNotice(Integer noticeId, Notice notice) {
        //首先对公告表的信息进行修改
        notice.setNoticeId(noticeId);
        //设置公告更新时间
        notice.setUpdateTime(new Date());
        int i = noticeMapper.updateByPrimaryKeySelective(notice);
        if (i > 0){
            //说明notice表信息修改成功，相应的要修改用户公告表，将结果查看结果修改为未查看
            Example example = new Example(UserNotice.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("noticeId",noticeId);
            UserNotice userNotice = new UserNotice();
            userNotice.setIsCheck(0);
            int k = userNoticeMapper.updateByExampleSelective(userNotice, example);
            if (k > 0){
                //说明修改成功
                return new ResultVO(ResStatus.OK,"公告信息修改成功！",notice);
            }
        }
        return new ResultVO(ResStatus.NO,"网络异常，信息修改失败！",null);
    }

    @Override
    public ResultVO queryDetails(Integer noticeId) {
        NoticeVo noticeVo = noticeMapper.queryDetails(noticeId);
        if (noticeVo !=  null){
            //说明详情查询成功
            return new ResultVO(ResStatus.OK,"success",noticeVo);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }
}
