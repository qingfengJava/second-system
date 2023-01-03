package com.qingfeng.cms.biz.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.manage.dto.InfoManageSaveDTO;
import com.qingfeng.cms.domain.manage.entity.InfoManageEntity;

/**
 * 
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-12-30 17:01:52
 */
public interface InfoManageService extends IService<InfoManageEntity> {

    /**
     * 保存信息管理数据
     * @param infoManageSaveDTO
     */
    void saveInfoManage(InfoManageSaveDTO infoManageSaveDTO);

}

