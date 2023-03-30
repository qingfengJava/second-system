package com.qingfeng.cms.biz.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.module.dto.CreditModuleQueryDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleSaveDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleUpdateDTO;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.ro.PlanTreeRo;
import com.qingfeng.cms.domain.plan.vo.PlanVo;

import java.util.List;

/**
 * 学分认定模块表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
public interface CreditModuleService extends IService<CreditModuleEntity> {

    /**
     * 保存学分认定模块
     * @param creditModuleSaveDTO
     */
    void saveCreditModule(CreditModuleSaveDTO creditModuleSaveDTO);

    /**
     * 修改学分认定模块信息
     * @param creditModuleUpdateDTO
     */
    void updateCreditModuleById(CreditModuleUpdateDTO creditModuleUpdateDTO);

    /**
     * 查询方案下的学分认定模块集合
     * @param page
     * @param creditModuleQueryDTO
     * @return
     */
    IPage<PlanVo> findList(IPage<PlanEntity> page, CreditModuleQueryDTO creditModuleQueryDTO);

    /**
     * 查询全部方案及关联的模块信息，并按年级进行排序
     * @return
     */
    List<PlanTreeRo> findPlanAndModule();

    /**
     * 删除学分认定模块
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 查询学生所属的方案模块列表
     * @param userId
     * @return
     */
    List<CreditModuleEntity> moduleListByStuId(Long userId);

    /**
     * 查询班级下的方案模块
     * @param userId
     * @return
     */
    List<CreditModuleEntity> moduleListByClazzId(Long userId);

}

