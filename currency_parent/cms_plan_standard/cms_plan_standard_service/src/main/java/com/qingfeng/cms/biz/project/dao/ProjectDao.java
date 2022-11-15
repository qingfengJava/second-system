package com.qingfeng.cms.biz.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目表
 * 
 * @author qingfeng
 * @email ${email}
 * @date 2022-10-08 19:44:16
 */
@Repository
public interface ProjectDao extends BaseMapper<ProjectEntity> {

    /**
     * 查询项目学分集合
     * @param projectQueryDTO
     * @return
     */
    List<ProjectListVo> selectAllList(@Param("projectQueryDTO") ProjectQueryDTO projectQueryDTO);
}
