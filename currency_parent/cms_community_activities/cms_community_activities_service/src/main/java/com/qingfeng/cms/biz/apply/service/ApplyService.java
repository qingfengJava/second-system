package com.qingfeng.cms.biz.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyUpdateDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.ro.ActiveApplyCheckRo;
import com.qingfeng.cms.domain.apply.ro.ActiveReleaseRo;
import com.qingfeng.cms.domain.apply.vo.ApplyCheckListVo;
import com.qingfeng.cms.domain.apply.vo.ApplyListVo;
import com.qingfeng.cms.domain.apply.vo.BonusFileVo;

/**
 * 社团活动申请表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
public interface ApplyService extends IService<ApplyEntity> {

    /**
     * 活动申请信息保存
     * @param applySaveDTO
     * @param userId
     */
    void saveApply(ApplySaveDTO applySaveDTO, Long userId);

    /**
     * 活动申请信息修改
     * @param applyUpdateDTO
     */
    void updateApplyById(ApplyUpdateDTO applyUpdateDTO);

    /**
     * 活动申请分页查询
     * @param applyQueryDTO
     */
    ApplyListVo findApplyList(ApplyQueryDTO applyQueryDTO, Long userId);

    /**
     * 根据Id删除申请的活动信息
     * @param id
     */
    void removeActiveById(Long id);

    /**
     * 活动申请信息审核
     * @param activeApplyCheckRo
     * @param userId
     */
    void activeApplyCheck(ActiveApplyCheckRo activeApplyCheckRo, Long userId);

    /**
     * 进行活动发布
     * @param activeReleaseRo
     */
    void activeRelease(ActiveReleaseRo activeReleaseRo);

    /**
     * 活动申请审核列表
     * @param applyCheckQueryDTO
     * @return
     */
    ApplyCheckListVo findApplyCheckList(ApplyCheckQueryDTO applyCheckQueryDTO);

    /**
     * 上传活动加分文件
     * @param bonusFileVo
     */
    void uploadBonusFile(BonusFileVo bonusFileVo);
}

