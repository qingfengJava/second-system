package com.qingfeng.dao;

import com.qingfeng.entity.Notice;
import com.qingfeng.entity.NoticeVo;
import com.qingfeng.generaldao.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统公告持久层
 *
 * @author 清风学Java
 */
@Repository
public interface NoticeMapper extends GeneralDao<Notice> {

    /**
     * 根据公告表主键Id查询公告详情信息
     *
     * @param noticeId
     * @return
     */
    NoticeVo queryDetails(Integer noticeId);

    /**
     * 分页查询公告信息列表
     *
     * @param isAdmin
     * @param start
     * @param limit
     * @return
     */
    List<NoticeVo> queryNotice(@Param("isAdmin") Integer isAdmin,
                               @Param("start") int start,
                               @Param("limit") int limit);

    /**
     * 查询公告的总记录数
     * @param isAdmin
     * @return
     */
    int selectCountNotice(Integer isAdmin);

    /**
     * 查询学生待处理的任务
     * @param isAdmin
     * @param start
     * @param limit
     * @return
     */
    List<NoticeVo> queryTaskNotice(@Param("isAdmin") Integer isAdmin,
                                      @Param("start") Integer start,
                                      @Param("limit") Integer limit);

    /**
     * 查询学生待处理任务的总记录数
     * @param isAdmin
     * @return
     */
    int selectCountTaskNotice(Integer isAdmin);
}