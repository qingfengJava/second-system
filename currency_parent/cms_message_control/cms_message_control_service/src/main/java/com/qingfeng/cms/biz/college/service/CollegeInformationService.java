package com.qingfeng.cms.biz.college.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.college.dto.CollegeInformationSaveDTO;
import com.qingfeng.cms.domain.college.dto.CollegeInformationUpdateDTO;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;

/**
 * 院级信息（包含班级），数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
public interface CollegeInformationService extends IService<CollegeInformationEntity> {

    /**
     * 保存二级学院用户关联的院系信息
     * @param collegeInformationSaveDTO
     * @param userId
     */
    void saveCollegeInformation(CollegeInformationSaveDTO collegeInformationSaveDTO, Long userId);

    /**
     * 根据用户Id查询关联的二级学院信息
     * @param userId
     * @return
     */
    CollegeInformationEntity getByUserId(Long userId);

    /**
     * 修改用户关联的二级学院的信息
     * @param collegeInformationUpdateDTO
     * @param userId
     */
    void updateCollegeInformationById(CollegeInformationUpdateDTO collegeInformationUpdateDTO, Long userId);
}

