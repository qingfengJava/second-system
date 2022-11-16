package com.qingfeng.cms.biz.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.project.dao.ProjectDao;
import com.qingfeng.cms.biz.project.enums.ProjectExceptionMsg;
import com.qingfeng.cms.biz.project.service.ProjectService;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.dto.ProjectUpdateDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;


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
    private DozerUtils dozer;
    @Autowired
    private RoleApi roleApi;

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
        //排除项目名字一样的内容
        ProjectEntity projectEntity = dozer.map2(projectSaveDTO, ProjectEntity.class);
        checkProject(projectEntity);

        // 需要判断用户角色，如果是二级学院领导（YUAN_LEVEL_LEADER），那么申报的项目就需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.STU_OFFICE_ADMIN.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是学院申请的项目
            // TODO 查询用户对应的学院信息

            //封装信息
            projectEntity.setDepartment("SJ");
            projectEntity.setIsCheck(ProjectCheckEnum.INIT);
        } else {
            // TODO 不是学院直接封装 PZHU
            projectEntity.setDepartment("PZHU");
            projectEntity.setIsCheck(ProjectCheckEnum.IS_FINISHED);
        }

        // TODO 如果是需要审核的，发送审核通知

        //保存
        baseMapper.insert(projectEntity);

    }

    /**
     * 修改模块项目内容
     *
     * @param projectUpdateDTO
     */
    @Override
    public void updateProjectById(ProjectUpdateDTO projectUpdateDTO, Long userId) {
        // 需要判断修改的内容是否已经重复
        ProjectEntity projectEntity = dozer.map2(projectUpdateDTO, ProjectEntity.class);
        checkProject(projectEntity);
        // TODO 需要判断用户角色
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.STU_OFFICE_ADMIN.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是学院申请的项目 要重新进行审核
            // TODO 查询用户对应的学院信息

            //封装信息
            projectEntity.setIsCheck(ProjectCheckEnum.INIT);
        } else {
            // TODO 不是学院直接封装 PZHU
            projectEntity.setDepartment("PZHU");
            projectEntity.setIsCheck(ProjectCheckEnum.IS_FINISHED);
        }

        baseMapper.updateById(projectEntity);
    }

    /**
     * 查询项目学分列表
     *  1、按条件进行筛选，
     *  2、排序
     * @param projectQueryDTO
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public List<ProjectListVo> findList(ProjectQueryDTO projectQueryDTO) {
        List<ProjectListVo> projectListVo = baseMapper.selectAllList(projectQueryDTO);
        // TODO 封装等级学分

        return projectListVo;
    }

    /**
     * 检查模块项目是否重复
     *
     * @param projectEntity
     */
    private void checkProject(ProjectEntity projectEntity) {
        ProjectEntity project = baseMapper.selectOne(Wraps.lbQ(new ProjectEntity())
                .eq(ProjectEntity::getModuleId, projectEntity.getModuleId())
                .like(ProjectEntity::getProjectName, projectEntity.getProjectName()));
        if (!ObjectUtils.isEmpty(project)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ProjectExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}