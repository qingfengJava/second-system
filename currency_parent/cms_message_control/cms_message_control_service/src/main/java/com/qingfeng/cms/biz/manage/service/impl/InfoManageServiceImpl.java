package com.qingfeng.cms.biz.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.manage.dao.InfoManageDao;
import com.qingfeng.cms.biz.manage.enums.InfoManageExceptionMsg;
import com.qingfeng.cms.biz.manage.service.InfoManageService;
import com.qingfeng.cms.domain.manage.dto.InfoManagePageDTO;
import com.qingfeng.cms.domain.manage.dto.InfoManageSaveDTO;
import com.qingfeng.cms.domain.manage.entity.InfoManageEntity;
import com.qingfeng.cms.domain.manage.enums.TypeStatusEnum;
import com.qingfeng.cms.domain.manage.vo.InfoManageListVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


/**
 * 
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-12-30 17:01:52
 */
@Service
public class InfoManageServiceImpl extends ServiceImpl<InfoManageDao, InfoManageEntity> implements InfoManageService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存信息管理数据
     * @param infoManageSaveDTO
     */
    @Override
    public void saveInfoManage(InfoManageSaveDTO infoManageSaveDTO) {
        //先要检查时间是否符合要求   开始时间必须大于当天
        if(LocalDate.now().isBefore(infoManageSaveDTO.getStartTime())){
            //同一时间范围内不能存在多个
            List<InfoManageEntity> infoManageEntities = baseMapper.selectList(Wraps.lbQ(new InfoManageEntity())
                    .leFooter(InfoManageEntity::getStartTime, infoManageSaveDTO.getEndTime())
                    .geHeader(InfoManageEntity::getEndTime, infoManageSaveDTO.getStartTime())
                    .in(InfoManageEntity::getTypeStatus, Arrays.asList(TypeStatusEnum.INIT, TypeStatusEnum.PROCESS)));
            if (CollUtil.isNotEmpty(infoManageEntities)){
                throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), InfoManageExceptionMsg.WRONG_DATE_RANGE.getDesc());
            }
            InfoManageEntity infoManageEntity = dozerUtils.map2(infoManageSaveDTO, InfoManageEntity.class);
            infoManageEntity.setTypeStatus(TypeStatusEnum.INIT);

            baseMapper.insert(infoManageEntity);
        }else{
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), InfoManageExceptionMsg.DATE_ERROR.getDesc());
        }
    }
}