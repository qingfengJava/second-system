package com.qingfeng.cms.biz.bonus.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.cms.domain.sign.vo.SingBonusPointsVo;

import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/3
 */
public class BonusExcelListener extends AnalysisEventListener<SingBonusPointsVo> {

    /**
     * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
     *
     * @param singBonusPointsVo
     * @param analysisContext
     */
    @Override
    public void invoke(SingBonusPointsVo singBonusPointsVo, AnalysisContext analysisContext) {
        // TODO 待学分管理的服务完善才能做
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
