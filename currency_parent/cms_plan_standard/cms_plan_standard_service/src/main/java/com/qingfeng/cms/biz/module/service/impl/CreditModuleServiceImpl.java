package com.qingfeng.cms.biz.module.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.module.dao.CreditModuleDao;
import com.qingfeng.cms.biz.module.enums.CreditModuleServiceExceptionMsg;
import com.qingfeng.cms.biz.module.service.CreditModuleService;
import com.qingfeng.cms.biz.plan.enums.PlanIsEnable;
import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.domain.module.dto.CreditModuleQueryDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleSaveDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleUpdateDTO;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.ro.ModuleTreeRo;
import com.qingfeng.cms.domain.module.vo.CreditModuleVo;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.ro.PlanTreeRo;
import com.qingfeng.cms.domain.plan.vo.PlanVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 学分认定模块表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Service("creditModuleService")
@Slf4j
public class CreditModuleServiceImpl extends ServiceImpl<CreditModuleDao, CreditModuleEntity> implements CreditModuleService {

    @Autowired
    private DozerUtils dozer;

    @Autowired
    private PlanService planService;

    /**
     * 保存学分认定模块：要判断模块名是否重复
     *
     * @param creditModuleSaveDTO
     */
    @Override
    public void saveCreditModule(CreditModuleSaveDTO creditModuleSaveDTO) {
        CreditModuleEntity creditModuleEntity = dozer.map2(creditModuleSaveDTO, CreditModuleEntity.class);
        checkCreditModule(creditModuleEntity);
        //没有重复的，可以直接进行保存
        baseMapper.insert(creditModuleEntity);
    }

    /**
     * 修改学分认定模块信息：要判断是否修改成重复的信息
     *
     * @param creditModuleUpdateDTO
     */
    @Override
    public void updateCreditModuleById(CreditModuleUpdateDTO creditModuleUpdateDTO) {
        CreditModuleEntity creditModuleEntity = dozer.map2(creditModuleUpdateDTO, CreditModuleEntity.class);
        checkCreditModule(creditModuleEntity);
        //没有异常，可以进行修改
        baseMapper.updateById(creditModuleEntity);
    }

    /**
     * 查询方案下的学分认定模块集合
     *
     * @param page
     * @param creditModuleQueryDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public IPage<PlanVo> findList(IPage<PlanEntity> page, CreditModuleQueryDTO creditModuleQueryDTO) {
        // 构建值不为null的查询条件
        LbqWrapper<PlanEntity> query = Wraps.lbQ(new PlanEntity())
                .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable())
                .orderByDesc(PlanEntity::getYear)
                .orderByDesc(PlanEntity::getGrade);

        //分页条件查询出启用的方案
        planService.page(page, query);
        //为每个方案构造子集，模块
        List<PlanVo> planVoList = page.getRecords().stream().map(p -> {
            PlanVo planVo = dozer.map2(p, PlanVo.class);
            // TODO 还可能有其他的条件构造
            CreditModuleEntity creditModuleEntity = dozer.map2(creditModuleQueryDTO, CreditModuleEntity.class);
            // 封装方案下对应的模块
            List<CreditModuleVo> moduleVoList = baseMapper.selectList(Wraps.lbQ(creditModuleEntity)
                            .eq(CreditModuleEntity::getPlanId, p.getId()))
                    .stream()
                    .map(c -> dozer.map2(c, CreditModuleVo.class))
                    .collect(Collectors.toList());

            planVo.setChildren(moduleVoList);

            return planVo;
        }).collect(Collectors.toList());

        // 转换成PlanVo对象
        IPage<PlanVo> iPage = new Page<PlanVo>();
        iPage.setRecords(planVoList);
        iPage.setTotal(page.getTotal());
        iPage.setSize(page.getSize());
        iPage.setCurrent(page.getCurrent());
        iPage.setPages(page.getPages());

        return iPage;
    }

    /**
     * 查询全部方案及关联的模块信息，并按年级进行分组排序
     *
     * @return
     */
    @Override
    public List<PlanTreeRo> findPlanAndModule() {
        // 构建值不为null的查询条件
        LbqWrapper<PlanEntity> query = Wraps.lbQ(new PlanEntity())
                .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable())
                .orderByDesc(PlanEntity::getGrade)
                .orderByAsc(PlanEntity::getApplicationObject);

        //分页条件查询出启用的方案
        List<PlanEntity> planEntityList = planService.list(query);
        List<PlanTreeRo> planTreeRoList = planEntityList.stream().map(p -> PlanTreeRo.builder()
                        .id(p.getId())
                        .planModuleLabel(p.getGrade() + "（"+
                                (p.getApplicationObject() == 1 ? "本科":"专科")
                                +"）" + p.getPlanName())
                        .build())
                .collect(Collectors.toList());

        //为每个方案构造子集，模块
        planTreeRoList.forEach(p -> {
            // 封装方案下对应的模块
            List<ModuleTreeRo> moduleTreeRoList = baseMapper.selectList(Wraps.lbQ(new CreditModuleEntity())
                            .eq(CreditModuleEntity::getPlanId, p.getId()))
                    .stream()
                    .map(c -> ModuleTreeRo.builder()
                            .id(c.getId())
                            .planModuleLabel(c.getModuleName())
                            .build())
                    .collect(Collectors.toList());

            p.setChildren(moduleTreeRoList);
        });

        return planTreeRoList;
    }

    private void checkCreditModule(CreditModuleEntity creditModuleEntity) {
        //检查是否有重名的模块名出现
        LbqWrapper<CreditModuleEntity> wrapper = Wraps.lbQ(new CreditModuleEntity())
                .like(CreditModuleEntity::getModuleName, creditModuleEntity.getModuleName())
                .eq(CreditModuleEntity::getPlanId, creditModuleEntity.getPlanId());

        LbqWrapper<CreditModuleEntity> lbqWrapper = Wraps.lbQ(new CreditModuleEntity())
                .eq(CreditModuleEntity::getPlanId, creditModuleEntity.getPlanId());

        if (ObjectUtil.isNotEmpty(creditModuleEntity.getId())) {
            wrapper.ne(CreditModuleEntity::getId, creditModuleEntity.getId());
            lbqWrapper.ne(CreditModuleEntity::getId, creditModuleEntity.getId());
        }

        CreditModuleEntity creditModule = baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNotEmpty(creditModule)) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), CreditModuleServiceExceptionMsg.IS_EXISTENCE.getMsg());
        }

        // 检查方案下的模块的总学分是否超过方案的总学分 方案Id是一定存在的
        PlanEntity planEntity = planService.getOne(Wraps.lbQ(new PlanEntity())
                .eq(PlanEntity::getId, creditModuleEntity.getPlanId()));

        int minSum = baseMapper.selectList(lbqWrapper)
                .stream()
                .mapToInt(CreditModuleEntity::getMinScore)
                .sum();

        if (planEntity.getTotalScore() < (minSum + creditModuleEntity.getMinScore())) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), CreditModuleServiceExceptionMsg.OUTOF_MIN.getMsg());
        }
    }
}