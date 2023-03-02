package com.qingfeng.cms.biz.sign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.sign.dto.ActiveApplySignQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveEvaluationDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveSignSaveDTO;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.cms.domain.sign.vo.ActiveApplySignVo;
import com.qingfeng.cms.domain.sign.vo.ApplyPageVo;
import com.qingfeng.cms.domain.sign.vo.OrganizeVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 活动报名表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
public interface ActiveSignService extends IService<ActiveSignEntity> {

    /**
     * 查询社团组织列表
     * @return
     */
    List<OrganizeVo> organizeList();

    /**
     * 查询已发布的活动
     * @param activeQueryDTO
     * @param userId
     * @return
     */
    ApplyPageVo applyList(ActiveQueryDTO activeQueryDTO, Long userId);

    /**
     * 活动报名
     * @param activeSignSaveDTO
     * @param userId
     */
    void saveSign(ActiveSignSaveDTO activeSignSaveDTO, Long userId);

    /**
     * 分页查询用户报名的活动信息
     * @param activeApplySignQueryDTO
     * @return
     */
    ActiveApplySignVo getActiveSignList(ActiveApplySignQueryDTO activeApplySignQueryDTO, Long userId);

    /**
     * 进行活动评价
     * @param activeEvaluationDTO
     */
    void setActivityEvaluation(ActiveEvaluationDTO activeEvaluationDTO);

    /**
     * 取消活动报名
     * @param id
     */
    void deleteById(Long id);

    /**
     * 查询报名的学生列表
     * @param applyId
     * @return
     */
    List<ActiveSignEntity> getStudentSignList(Long applyId);

    /**
     * 导出学生报名加分表
     * @param response
     */
    void exportStuBonusPoints(HttpServletResponse response, Long applyId);
}

