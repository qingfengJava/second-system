package com.qingfeng.cms.biz.feedback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.feedback.dto.SystemFeedbackSaveDTO;
import com.qingfeng.cms.domain.feedback.entity.SystemFeedbackEntity;
import com.qingfeng.cms.domain.feedback.vo.UserLeaderVo;

import java.util.List;

/**
 * 系统反馈表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
public interface SystemFeedbackService extends IService<SystemFeedbackEntity> {

    /**
     * 保存系统反馈的信息
     * @param systemFeedbackSaveDTO
     * @param userId
     */
    void saveSystemFeedback(SystemFeedbackSaveDTO systemFeedbackSaveDTO, Long userId);

    /**
     * 查询当前用户领导Id
     * @param userId
     * @return
     */
    List<UserLeaderVo> getLeader(Long userId);
}

