package com.qingfeng.cms.biz.bonus.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.bonus.dao.BonusCheckDao;
import com.qingfeng.cms.biz.bonus.listener.BonusExcelListener;
import com.qingfeng.cms.biz.bonus.service.BonusCheckService;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckQueryDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckSaveDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusCheckEntity;
import com.qingfeng.cms.domain.bonus.ro.BonusCheckRo;
import com.qingfeng.cms.domain.bonus.vo.BonusCheckVo;
import com.qingfeng.cms.domain.sign.vo.SingBonusPointsVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * 加分文件审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
@Service
public class BonusCheckServiceImpl extends ServiceImpl<BonusCheckDao, BonusCheckEntity> implements BonusCheckService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private ApplyService applyService;

    /**
     * 查询需要加分审核的活动列表
     *
     * @param bonusCheckQueryDTO
     * @return
     */
    @Override
    public BonusCheckVo bonusList(BonusCheckQueryDTO bonusCheckQueryDTO) {
        Integer pageNo = bonusCheckQueryDTO.getPageNo();
        Integer pageSize = bonusCheckQueryDTO.getPageSize();
        // 查询需要加分审核的活动数
        LbqWrapper<BonusCheckEntity> queryWrapper = Wraps.lbQ(new BonusCheckEntity());
        if (ObjectUtil.isNotEmpty(bonusCheckQueryDTO.getCheckStatus())) {
            queryWrapper.eq(BonusCheckEntity::getCheckStatus, bonusCheckQueryDTO.getCheckStatus());
        }
        Integer total = baseMapper.selectCount(queryWrapper);
        if (total == 0) {
            return BonusCheckVo.builder()
                    .total(total)
                    .bonusList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }

        // 链表查询需要的数据
        List<BonusCheckRo> bonusCheckRoList = baseMapper.bonusList(bonusCheckQueryDTO);

        return BonusCheckVo.builder()
                .total(total)
                .bonusList(bonusCheckRoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 活动加分审核结果保存
     *
     * @param bonusCheckSaveDTO
     */
    @Override
    public void updateBonus(BonusCheckSaveDTO bonusCheckSaveDTO) throws IOException {
        baseMapper.updateById(dozerUtils.map2(bonusCheckSaveDTO, BonusCheckEntity.class));

        // 获取加分文件的连接
        String bonusFile = applyService.getById(
                baseMapper.selectById(bonusCheckSaveDTO.getId())
                        .getApplyId()
        ).getBonusFile();

        // TODO 待学分服务完善才能做
        InputStream inputStream = new URL(bonusFile).openConnection().getInputStream();
        EasyExcel.read(
                        inputStream,
                        SingBonusPointsVo.class,
                        new BonusExcelListener()
                ).sheet()
                .doRead();
    }
}