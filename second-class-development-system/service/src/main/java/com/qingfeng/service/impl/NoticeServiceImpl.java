package com.qingfeng.service.impl;

import com.qingfeng.dao.NoticeMapper;
import com.qingfeng.entity.Notice;
import com.qingfeng.service.NoticeService;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
