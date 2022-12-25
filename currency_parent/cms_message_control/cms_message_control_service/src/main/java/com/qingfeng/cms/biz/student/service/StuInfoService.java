package com.qingfeng.cms.biz.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;

/**
 * 学生信息详情表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
public interface StuInfoService extends IService<StuInfoEntity> {

    /**
     * 保存用户详情信息
     * @param stuInfoSaveDTO
     */
    void saveStuInfo(StuInfoSaveDTO stuInfoSaveDTO);
}

