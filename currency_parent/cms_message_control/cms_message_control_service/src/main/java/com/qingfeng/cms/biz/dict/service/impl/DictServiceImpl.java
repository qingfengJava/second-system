package com.qingfeng.cms.biz.dict.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.dict.dao.DictDao;
import com.qingfeng.cms.biz.dict.enums.DictServiceExceptionMsg;
import com.qingfeng.cms.biz.dict.service.DictService;
import com.qingfeng.cms.constant.CacheKey;
import com.qingfeng.cms.domain.dict.dto.DictSaveDTO;
import com.qingfeng.cms.domain.dict.dto.DictUpdateDTO;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.cms.domain.dict.vo.DictVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织架构表   数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:27
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, DictEntity> implements DictService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private CacheChannel cacheChannel;

    /**
     * 查询数据字典架构树
     *
     * @return
     */
    @Override
    public List<DictVo> findListTree() {
        CacheObject cacheObject = cacheChannel.get(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_TREE);
        if (ObjectUtil.isEmpty(cacheObject.getValue())) {
            List<DictEntity> dictList = baseMapper.selectList(null);

            //2、组装成父子的树形结构
            //2.1 找到所有的一级分类
            List<DictVo> dictVoList = dictList.stream().filter(d ->
                    d.getParentId() == 0
            ).map(dict -> {
                //将上一步的结果通过map映射再一次进行数据操作
                //map操作，把当前分类转换成一个新的对象  dict 就是过滤出的一个个dict对象
                return dozerUtils.map2(dict, DictVo.class)
                        .setChildren(getChildrens(dict, dictList));
            }).collect(Collectors.toList());
            cacheChannel.set(CacheKey.MESSAGE_RESOURCE,CacheKey.DICT_TREE, dictVoList);
            return dictVoList;
        } else {
            return (List<DictVo>) cacheObject.getValue();
        }
    }

    /**
     * 添加数据字典内容
     * @param dictSaveDTO
     */
    @Override
    public void saveDict(DictSaveDTO dictSaveDTO) {
        // 排查同级下面是否有相同名字和相同编码的内容
        DictEntity dictEntity = dozerUtils.map2(dictSaveDTO, DictEntity.class);
        checkDict(dictEntity);
        baseMapper.insert(dictEntity);

        //更新数据字典对应的值
        dictEntity.setDictValue(String.valueOf(dictEntity.getId()));
        baseMapper.updateById(dictEntity);
    }

    /**
     * 根据Id修改数据字典内容
     * @param dictUpdateDTO
     */
    @Override
    public void updateDictById(DictUpdateDTO dictUpdateDTO) {
        // 排查同级下面是否有相同名字和相同编码的内容
        DictEntity dictEntity = dozerUtils.map2(dictUpdateDTO, DictEntity.class);
        checkDict(dictEntity);
        baseMapper.updateById(dictEntity);
    }

    private void checkDict(DictEntity dict) {
        DictEntity dictEntity = baseMapper.selectOne(Wraps.lbQ(new DictEntity())
                .like(DictEntity::getDictName, dict.getDictName()));
        if (ObjectUtil.isNotEmpty(dictEntity)){
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), DictServiceExceptionMsg.IS_EXISTENCE.getMsg());
        }

        // 再排查编码是否是唯一的
        if(ObjectUtil.isNotEmpty(dict.getDictCode())){
            List<DictEntity> dictList = baseMapper.selectList(Wraps.lbQ(new DictEntity())
                    .eq(DictEntity::getDictCode, dict.getDictCode()));
            if (ObjectUtil.isNotEmpty(dictList)){
                throw new BizException(ExceptionCode.OPERATION_EX.getCode(), DictServiceExceptionMsg.IS_EXISTCODE.getMsg());
            }
        }
    }


    /**
     * 递归查询出所有的子集
     *
     * @param dict
     * @param dictList
     * @return
     */
    private List<DictVo> getChildrens(DictEntity dict, List<DictEntity> dictList) {
        // 过滤所有菜单的父Id等于当前菜单的id
        return dictList.stream()
                .filter(d -> d.getParentId().equals(dict.getId()))
                .map(dictEntity -> {
                    // 找到子集
                    return dozerUtils.map2(dictEntity, DictVo.class)
                            .setChildren(getChildrens(dictEntity, dictList));
                }).collect(Collectors.toList());
    }
}