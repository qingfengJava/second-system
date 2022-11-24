package com.qingfeng.cms.biz.dict.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.cms.biz.dict.dao.DictDao;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.cms.domain.dict.vo.DictExcelVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;

import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/24
 */
public class DictExcelListener extends AnalysisEventListener<DictExcelVo> {

    private DictDao dictDao;
    private DozerUtils dozerUtils;

    /**
     * 使用有参的构造方法，传入需要的参数对象
     *
     * @param dictDao
     * @param dozerUtils
     */
    public DictExcelListener(DictDao dictDao, DozerUtils dozerUtils) {
        this.dictDao = dictDao;
        this.dozerUtils = dozerUtils;
    }

    /**
     * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
     *
     * @param dictExcelVo
     * @param analysisContext
     */
    @Override
    public void invoke(DictExcelVo dictExcelVo, AnalysisContext analysisContext) {
        //同一父级下不能有重复的内容
        DictEntity dictEntity = dictDao.selectOne(Wraps.lbQ(new DictEntity())
                .eq(DictEntity::getParentId, dictExcelVo.getParentId())
                .eq(DictEntity::getDictName, dictExcelVo.getDictName()));
        // TODO 逻辑问题是否可以再优化一下
        if (ObjectUtil.isEmpty(dictEntity)) {
            //不存在，就可以直接进行存储
            dictDao.insert(dozerUtils.map2(dictExcelVo, DictEntity.class));
        } else {
            //存在则更新
            dictEntity.setDictName(dictExcelVo.getDictName())
                    .setParentId(dictExcelVo.getParentId())
                    .setDictValue(dictExcelVo.getDictValue())
                    .setDictCode(dictExcelVo.getDictCode())
                    .setId(dictExcelVo.getId());
            dictDao.insert(dictEntity);

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
