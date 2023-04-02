package com.qingfeng.cms.biz.bonus.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.club.dto.ClubScoreModuleSaveDTO;
import com.qingfeng.cms.domain.sign.vo.SingBonusPointsVo;
import com.qingfeng.sdk.school.club.ClubScoreModuleApi;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/3
 */
@Slf4j
public class BonusExcelListener extends AnalysisEventListener<SingBonusPointsVo> {

    ApplyEntity applyEntity;
    ClubScoreModuleApi clubScoreModuleApi;

    public BonusExcelListener(ApplyEntity applyEntity, ClubScoreModuleApi clubScoreModuleApi) {
        this.applyEntity = applyEntity;
        this.clubScoreModuleApi = clubScoreModuleApi;
    }

    /**
     * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
     *
     * @param singBonusPointsVo
     * @param analysisContext
     */
    @Override
    public void invoke(SingBonusPointsVo singBonusPointsVo, AnalysisContext analysisContext) {
        try {
            // 需要直接进行加分的
            clubScoreModuleApi.save(ClubScoreModuleSaveDTO.builder()
                    .userId(singBonusPointsVo.getUserId())
                    .activeApplyId(applyEntity.getId())
                    .score(BigDecimal.valueOf(applyEntity.getActiveScore()))
                    .schoolYear(applyEntity.getSchoolYear())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("加分出错：学生：{}，活动：{}，信息：{}",singBonusPointsVo.getUserId(), applyEntity.getId(), e);
        }
    }

    /**
     * 读取Excel表头第一行的数据
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {

    }

    /**
     * 读取Excel结束后调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
