package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.NoticeMapper;
import com.qingfeng.dao.UserNoticeMapper;
import com.qingfeng.entity.Notice;
import com.qingfeng.entity.UserNotice;
import com.qingfeng.service.UserNoticeService;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/11
 */
@Service
public class UserNoticeServiceImpl implements UserNoticeService {

    @Autowired
    private UserNoticeMapper userNoticeMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 添加用户公告查询信息
     * @param uid
     * @param noticeId
     * @return
     */
    @Override
    @Transactional
    public ResultVO addUserNotice(Integer uid, Integer noticeId) {
        //添加要注意的事项，要查询是否已经存在该条信息，因为存在修改公告的情况下，会重新标记
        Example example = new Example(UserNotice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        criteria.andEqualTo("noticeId",noticeId);
        UserNotice userNotice = userNoticeMapper.selectOneByExample(example);
        if (userNotice != null){
            if (userNotice.getIsCheck() == 0){
                userNotice.setIsCheck(1);
                userNotice.setCreateTime(new Date());
            }
            userNotice.setUpdateTime(new Date());
            userNoticeMapper.updateByPrimaryKeySelective(userNotice);
        }else {
            userNotice = new UserNotice();
            userNotice.setUid(uid);
            userNotice.setNoticeId(noticeId);
            userNotice.setIsCheck(1);
            userNotice.setCreateTime(new Date());
            userNotice.setUpdateTime(new Date());
            userNoticeMapper.insertUseGeneratedKeys(userNotice);
        }

        //给公告的查看人数+1
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        notice.setReadNum(notice.getReadNum()+1);
        noticeMapper.updateByPrimaryKeySelective(notice);

        return new ResultVO(ResStatus.OK,"success",null);
    }
}
