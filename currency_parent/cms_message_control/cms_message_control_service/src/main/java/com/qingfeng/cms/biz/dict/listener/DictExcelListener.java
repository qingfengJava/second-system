package com.qingfeng.cms.biz.dict.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.qingfeng.cms.biz.dict.service.DictService;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.cms.domain.dict.vo.DictExcelVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/24
 */
public class DictExcelListener extends AnalysisEventListener<DictExcelVo> {

    private DictService dictService;
    private DozerUtils dozerUtils;

    /**
     * 每隔100条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<DictEntity> cachedAddList = new ArrayList<>(BATCH_COUNT);
    private List<DictEntity> cachedUpdateList = new ArrayList<>(BATCH_COUNT);



    /**
     * 使用有参的构造方法，传入需要的参数对象
     *
     * @param dictService
     * @param dozerUtils
     */
    public DictExcelListener(DictService dictService, DozerUtils dozerUtils) {
        this.dictService = dictService;
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
        DictEntity dictEntity = dictService.getOne(Wraps.lbQ(new DictEntity())
                .eq(DictEntity::getParentId, dictExcelVo.getParentId())
                .eq(DictEntity::getDictName, dictExcelVo.getDictName()));
        if (ObjectUtil.isEmpty(dictEntity)) {
            //不存在，就可以直接进行存储
            cachedAddList.add(dozerUtils.map2(dictExcelVo, DictEntity.class));
        } else {
            //存在则更新
            dictEntity.setDictName(dictExcelVo.getDictName())
                    .setParentId(dictExcelVo.getParentId())
                    .setDictValue(dictExcelVo.getDictValue())
                    .setDictCode(dictExcelVo.getDictCode())
                    .setId(dictExcelVo.getId());
            cachedUpdateList.add(dictEntity);

        }

        //执行
        if (cachedAddList.size() >= BATCH_COUNT) {
                dictService.saveBatch(cachedAddList);
                cachedAddList.clear();
        }
        if (cachedUpdateList.size() >= BATCH_COUNT) {
                dictService.updateBatchById(cachedUpdateList);
                cachedUpdateList.clear();
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

        if (CollUtil.isNotEmpty(cachedAddList)) {
                    //确保最后的数据能够存储
                    dictService.saveBatch(cachedAddList);
        }

        if (CollUtil.isNotEmpty(cachedUpdateList)) {
                    //确保最后的数据能够存储
                    dictService.updateBatchById(cachedUpdateList);

        }
    }
}
