package com.qingfeng.cms.biz.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.project.dao.ProjectDao;
import com.qingfeng.cms.biz.project.service.ProjectService;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 项目表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Service("projectService")
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectDao, ProjectEntity> implements ProjectService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存模块项目内容
     * 注意事项：判断用户角色，主要是院级申请的项目，需要进行审核
     *
     * @param projectSaveDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveProject(ProjectSaveDTO projectSaveDTO, Long userId) {
        // TODO 需要判断用户角色，如果是二级学院领导（YUAN_LEVEL_LEADER），那么申报的项目就需要审核

    }
}