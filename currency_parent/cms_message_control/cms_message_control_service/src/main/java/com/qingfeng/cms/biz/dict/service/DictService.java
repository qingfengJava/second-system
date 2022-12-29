package com.qingfeng.cms.biz.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.dict.dto.DictSaveDTO;
import com.qingfeng.cms.domain.dict.dto.DictUpdateDTO;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.cms.domain.dict.vo.DictVo;

import java.util.List;

/**
 * 组织架构表   数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:27
 */
public interface DictService extends IService<DictEntity> {

    /**
     * 查询数据字典架构树
     * @return
     */
    List<DictVo> findListTree();

    /**
     * 添加数据字典内容
     * @param dictSaveDTO
     */
    void saveDict(DictSaveDTO dictSaveDTO);

    /**
     * 根据id修改数据字典内容
     * @param dictUpdateDTO
     */
    void updateDictById(DictUpdateDTO dictUpdateDTO);

    /**
     * 查询所有院系信息
     * @return
     */
    List<DictEntity> findDepartment();

    /**
     * 根据院系Id查询对应的专业信息
     * @param depId
     * @return
     */
    List<DictEntity> findMajorByDepId(Long depId);

    /**
     * 查询民族
     * @return
     */
    List<DictEntity> findNation();

    /**
     * 查询籍贯信息
     * @return
     */
    List<DictVo> findNativePlace();
}

