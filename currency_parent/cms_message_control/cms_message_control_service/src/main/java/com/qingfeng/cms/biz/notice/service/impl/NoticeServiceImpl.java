package com.qingfeng.cms.biz.notice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.notice.dao.NoticeDao;
import com.qingfeng.cms.biz.notice.dao.UserNoticeCheckDao;
import com.qingfeng.cms.biz.notice.service.NoticeService;
import com.qingfeng.cms.domain.notice.dto.NoticeQueryDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeSaveDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeUpdateDTO;
import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import com.qingfeng.cms.domain.notice.entity.UserNoticeCheckEntity;
import com.qingfeng.cms.domain.notice.vo.NoticePageVo;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 系统公告表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, NoticeEntity> implements NoticeService {

    @Autowired
    private UserNoticeCheckDao userNoticeCheckDao;
    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private UserRoleApi userRoleApi;

    /**
     * 发布第二课堂公告
     *
     * @param noticeSaveDTO
     * @param userId
     */
    @Override
    public void publishNotice(NoticeSaveDTO noticeSaveDTO, Long userId) {
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        NoticeEntity noticeEntity = dozerUtils.map2(noticeSaveDTO, NoticeEntity.class);
        noticeEntity.setUserId(userId)
                .setUserCode(userRoleVo.getCode())
                .setReadNum(0);

        baseMapper.insert(noticeEntity);
    }

    /**
     * 修改已发布的公告信息
     *
     * @param noticeUpdateDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void updateNoticeById(NoticeUpdateDTO noticeUpdateDTO, Long userId) {
        NoticeEntity noticeEntity = dozerUtils.map2(noticeUpdateDTO, NoticeEntity.class);
        noticeEntity.setReadNum(0);

        // 进行修改
        baseMapper.updateById(noticeEntity);

        // 需要删除之前用户已经阅读过的记录
        userNoticeCheckDao.delete(
                Wraps.lbQ(new UserNoticeCheckEntity())
                        .eq(UserNoticeCheckEntity::getNoticeId, noticeEntity.getId())
        );

    }

    /**
     * 根据Id查询公告信息
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public NoticeEntity getNoticeById(Long id, Long userId) {
        // 查询公告信息
        NoticeEntity noticeEntity = baseMapper.selectById(id);
        // 查看公告信息，需要看是否是本人查看，如果不是本人，那就需要记录用户的查看记录， 并且修改阅读量
        if (!noticeEntity.getUserId().equals(userId)) {
            noticeEntity.setReadNum(noticeEntity.getReadNum() + 1);
            baseMapper.updateById(noticeEntity);

            // 保存用户查看记录
            userNoticeCheckDao.insert(
                    UserNoticeCheckEntity.builder()
                            .userId(userId)
                            .noticeId(noticeEntity.getId())
                            .build()
            );
        }

        return noticeEntity;
    }

    /**
     * 根据Id删除第二课堂公告信息，连同用户查看记录一并删除
     * @param id
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void deleteNoticeById(Long id) {
        baseMapper.deleteById(id);
        // 需要删除之前用户已经阅读过的记录
        userNoticeCheckDao.delete(
                Wraps.lbQ(new UserNoticeCheckEntity())
                        .eq(UserNoticeCheckEntity::getNoticeId, id)
        );
    }

    /**
     * 查询用户自己发布的公告信息
     * @param noticeQueryDTO
     * @param userId
     * @return
     */
    @Override
    public NoticePageVo noticeList(NoticeQueryDTO noticeQueryDTO, Long userId) {
        Integer pageNo = noticeQueryDTO.getPageNo();
        Integer pageSize = noticeQueryDTO.getPageSize();

        // 先查询总计数
        LbqWrapper<NoticeEntity> wrapper = Wraps.lbQ(new NoticeEntity())
                .eq(NoticeEntity::getUserId, userId);
        if (StrUtil.isNotEmpty(noticeQueryDTO.getTitle())) {
            wrapper.eq(NoticeEntity::getTitle, noticeQueryDTO.getTitle());
        }
        Integer count = baseMapper.selectCount(wrapper);
        if (count == 0) {
            return NoticePageVo.builder()
                    .total(count)
                    .noticeList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }

        noticeQueryDTO.setPageNo((pageNo - 1) * pageSize);
        List<NoticeEntity> noticeList = baseMapper.selectNoticeList(noticeQueryDTO, userId);

        return NoticePageVo.builder()
                .total(count)
                .noticeList(noticeList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }
}