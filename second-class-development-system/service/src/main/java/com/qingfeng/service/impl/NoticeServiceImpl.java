package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.NoticeMapper;
import com.qingfeng.dao.UserNoticeMapper;
import com.qingfeng.entity.Notice;
import com.qingfeng.entity.UserNotice;
import com.qingfeng.service.NoticeService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.NoticeVo;
import com.qingfeng.vo.ResultVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

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
    @CacheEvict(value = "notice", allEntries=true)
    public ResultVO addNotice(Integer userId, Notice notice) {
        //设置基本信息
        notice.setUserId(userId);
        notice.setReadNum(0);
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
    @CacheEvict(value = "notice", allEntries=true)
    public ResultVO updateNotice(Integer noticeId, Notice notice) {
        //首先对公告表的信息进行修改
        notice.setNoticeId(noticeId);
        //设置公告更新时间
        notice.setUpdateTime(new Date());
        notice.setReadNum(0);
        int i = noticeMapper.updateByPrimaryKeySelective(notice);
        if (i > 0){
            //说明notice表信息修改成功，相应的要修改用户公告表，将结果查看结果修改为未查看
            Example example = new Example(UserNotice.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("noticeId",noticeId);
            //先查询用户信息表有没有数据，有数据再进行修改
            List<UserNotice> userNotices = userNoticeMapper.selectByExample(example);
            if (userNotices.size() > 0){
                UserNotice userNotice = new UserNotice();
                userNotice.setIsCheck(0);
                int k = userNoticeMapper.updateByExampleSelective(userNotice, example);
            }
            //说明修改成功
            return new ResultVO(ResStatus.OK,"公告信息修改成功！",notice);
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

    /**
     * 分页查询公告列表
     * 注意：对于用户来说   要分已查看和未查看的
     * @param pageNum
     * @param limit
     * @return
     */
    @Override
    @Cacheable(value = "notice",keyGenerator = "keyGenerator")
    public ResultVO queryNotice(Integer isAdmin, int pageNum, int limit) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            List<NoticeVo> noticeList = noticeMapper.queryNotice(isAdmin,start,limit);
            //查询总计录数   注意是所有的
            int count = noticeMapper.selectCountNotice(isAdmin);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<NoticeVo> pageHelper = new PageHelper<>(count, pageCount, noticeList);
            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    /**
     * 查询学生需要处理的公告任务
     * @param isAdmin
     * @param pageNum
     * @param limit
     * @return
     */
    @Override
    public ResultVO queryWaitTask(Integer isAdmin, Integer pageNum, Integer limit) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            List<NoticeVo> noticeTaskList = noticeMapper.queryTaskNotice(isAdmin,start,limit);
            System.out.println(noticeTaskList);
            int count = noticeMapper.selectCountTaskNotice(isAdmin);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<NoticeVo> pageHelper = new PageHelper<>(count, pageCount, noticeTaskList);
            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    /**
     * 根据用户Id分页条件查询公告列表
     * @param userId
     * @param pageNum
     * @param limit
     * @param title
     * @param status
     * @return
     */
    @Override
    @Cacheable(value = "notice",keyGenerator = "keyGenerator")
    public PageHelper<Notice> queryNoticeListByUserId(Integer userId, int pageNum, int limit, String title, Integer status) {
        int start = (pageNum - 1) * limit;
        RowBounds rowBounds = new RowBounds(start, limit);
        Example example = new Example(Notice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andLike("title","%"+title+"%");
        if (status != null && status > -1){
            criteria.andEqualTo("isTask",status);
        }
        List<Notice> notices = noticeMapper.selectByExampleAndRowBounds(example, rowBounds);
        int count = noticeMapper.selectCountByExample(example);
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        PageHelper<Notice> pageHelper = new PageHelper<>(count, pageCount, notices);
        return pageHelper;
    }

}
