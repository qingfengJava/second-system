package com.qingfeng.cms.biz.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;

/**
 * 项目表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
public interface ProjectService extends IService<ProjectEntity> {

    /**
     * 保存模块项目内容
     * @param projectSaveDTO
     * @param userId
     */
    void saveProject(ProjectSaveDTO projectSaveDTO, Long userId);
}

