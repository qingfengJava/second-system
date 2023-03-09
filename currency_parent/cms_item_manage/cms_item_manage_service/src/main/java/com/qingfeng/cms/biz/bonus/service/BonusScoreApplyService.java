package com.qingfeng.cms.biz.bonus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyPageDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplySaveDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyUpdateDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import com.qingfeng.cms.domain.bonus.vo.BonusScoreApplyVo;
import com.qingfeng.cms.domain.bonus.vo.BonusScorePageVo;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;

import java.util.List;

/**
 * 加分申报表（提交加分细则申请）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
public interface BonusScoreApplyService extends IService<BonusScoreApplyEntity> {

    /**
     * 分页查询加分申报表
     * @param bonusScoreApplyPageDTO
     * @return
     */
    BonusScorePageVo findBonusScorePage(BonusScoreApplyPageDTO bonusScoreApplyPageDTO, Long userId);

    /**
     * 根据模块Id查询项目等级学分信息
     * @param moduleId
     * @param userId
     * @return
     */
    List<ProjectListVo> projectList(Long moduleId, Long userId);

    /**
     * 项目加分申报
     * @param bonusScoreApplySaveDTO
     * @param userId
     */
    void saveBonusScoreApply(BonusScoreApplySaveDTO bonusScoreApplySaveDTO, Long userId);

    /**
     * 取消项目加分申报
     * @param id
     */
    void cancelBonusById(Long id);

    /**
     * 重新上传证明材料
     * @param bonusScoreApplyUpdateDTO
     */
    void updateBonusScoreApply(BonusScoreApplyUpdateDTO bonusScoreApplyUpdateDTO);

    /**
     * 查询当日报名的项目信息
     * @param userId
     * @return
     */
    List<BonusScoreApplyVo> findBonusScoreSameDay(Long userId);

}

