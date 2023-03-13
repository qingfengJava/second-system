package com.qingfeng.cms.biz.clazz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.clazz.dto.ClazzInfoSaveDTO;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.cms.domain.clazz.vo.UserVo;

import java.util.List;

/**
 * 班级信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-12 22:37:25
 */
public interface ClazzInfoService extends IService<ClazzInfoEntity> {

    /**
     * 保存/修改班级信息
     * @param clazzInfoSaveDTO
     */
    void saveClazzInfo(ClazzInfoSaveDTO clazzInfoSaveDTO, Long userId);

    /**
     * 查询班级下的学生信息
     * @param userId
     * @return
     */
    List<UserVo> stuList(Long userId);
}

