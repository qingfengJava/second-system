package com.qingfeng.cms.biz.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.dto.ProjectUpdateDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;

import java.util.List;

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

    /**
     * 修改模块项目内容
     * @param projectUpdateDTO
     * @param userId
     */
    void updateProjectById(ProjectUpdateDTO projectUpdateDTO, Long userId);

    /**
     * 查询项目学分列表
     * @param projectQueryDTO
     * @return List<ProjectListVo>
     */
    List<ProjectListVo> findList(ProjectQueryDTO projectQueryDTO);
}

