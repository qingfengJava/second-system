package com.qingfeng.cms.biz.plan.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.module.dao.CreditModuleDao;
import com.qingfeng.cms.biz.plan.dao.PlanDao;
import com.qingfeng.cms.biz.plan.enums.PlanExceptionMsg;
import com.qingfeng.cms.biz.plan.enums.PlanIsEnable;
import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;


/**
 * 方案设定表（是否是修读标准，本科标准，专科标准）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Service("planService")
@Slf4j
public class PlanServiceImpl extends ServiceImpl<PlanDao, PlanEntity> implements PlanService {

    @Autowired
    private CreditModuleDao creditModuleDao;

    /**
     * 保存方案：新增保存方案的时候要判断该类型是否已经有启用的方案
     *
     * @param plan
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void savePlan(PlanEntity plan) {
        //先判断当前要保存的方案是否启用
        if (plan.getIsEnable().equals(PlanIsEnable.ENABLE_TURE.getEnable())) {
            //查询数据库里面是否已经有启用的方案
            PlanEntity planEntity = baseMapper.selectOne(Wraps.lbQ(new PlanEntity())
                    .eq(PlanEntity::getGrade, plan.getGrade())
                    .eq(PlanEntity::getIsEnable, plan.getIsEnable())
                    .eq(PlanEntity::getApplicationObject, plan.getApplicationObject()));
            if (!ObjectUtils.isEmpty(planEntity)) {
                throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), PlanExceptionMsg.IS_EXISTENCE.getMsg());
            }
            //否则可以保存，就作为唯一的父类
            plan.setParentId(0L);

            //直接将其子类的父级Id进行修改
            baseMapper.update(new PlanEntity(), Wraps.lbU(new PlanEntity())
                    .eq(PlanEntity::getYear, plan.getYear())
                    .eq(PlanEntity::getGrade, plan.getGrade())
                    .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_NOT.getEnable())
                    .eq(PlanEntity::getApplicationObject, plan.getApplicationObject())
                    .set(PlanEntity::getParentId, plan.getId()));
        } else {
            //如果是不启用的，那么先查询是否有父类
            PlanEntity planEntity = baseMapper.selectOne(Wraps.lbQ(new PlanEntity())
                    .eq(PlanEntity::getGrade, plan.getGrade())
                    .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable())
                    .eq(PlanEntity::getApplicationObject, plan.getApplicationObject()));
            //如果没有父类，就0，有父类就是父类id
            plan.setParentId(ObjectUtils.isEmpty(planEntity) ? 0L : planEntity.getId());

        }

        //最终直接添加即可
        baseMapper.insert(plan);

    }

    /**
     * 修改方案：
     * 修改的时候，要主要看看是否已经有启用的方案。
     * 还要注意，和模块相互关联的。
     * ① 如果没有任何关联，那么，唯一的启用可以关闭，否则不能关闭
     * ② 如果如果要开启启用，那么需要看看以前有没有启用
     * 有的话，将以前的启用先关闭，再开启现在的，并查看是否有关联，数据一并进行修改。
     *
     * @param plan
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void updatePlan(PlanEntity plan) {
        //首先判断是修改成什么类型
        if (plan.getIsEnable().equals(PlanIsEnable.ENABLE_NOT.getEnable())) {
            //如果本身自己就是为启用的状态，则不进行下面的操作
            if (baseMapper.selectById(plan.getId()).getIsEnable().equals(PlanIsEnable.ENABLE_TURE.getEnable())){
                //取消启用，查看是否有关联的
                List<CreditModuleEntity> moduleList = creditModuleDao.selectList(new LambdaQueryWrapper<CreditModuleEntity>()
                        .eq(CreditModuleEntity::getPlanId, plan.getId()));
                if (ObjectUtil.isNotEmpty(moduleList) && moduleList.size() > 0) {
                    //说明有关联，不能关闭
                    throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), PlanExceptionMsg.IS_RELATED.getMsg());
                }
                // 没有关联，要取消启用，就需要修改其子类的父级Id
                baseMapper.update(new PlanEntity(), Wraps.lbU(new PlanEntity())
                        .eq(PlanEntity::getGrade, plan.getGrade())
                        .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_NOT.getEnable())
                        .eq(PlanEntity::getApplicationObject, plan.getApplicationObject())
                        .set(PlanEntity::getParentId, 0L));
            }
        } else {
            plan.setParentId(0L);

            //要修改成启用的 is_Enable == 1 的情况，先查看以前是否有启用的情况
            PlanEntity planEntity = baseMapper.selectOne(Wraps.lbQ(new PlanEntity())
                    .eq(PlanEntity::getGrade, plan.getGrade())
                    .eq(PlanEntity::getIsEnable, plan.getIsEnable())
                    .eq(PlanEntity::getApplicationObject, plan.getApplicationObject()));
            if (ObjectUtil.isNotEmpty(planEntity)) {
                //说明以前有关联，把以前关联的状态进行改变
                planEntity.setIsEnable(PlanIsEnable.ENABLE_NOT.getEnable());
                baseMapper.updateById(planEntity);

                //查询出以前的关联上的模块，并且修改他们关联的方案
                creditModuleDao.selectList(Wraps.lbQ(new CreditModuleEntity())
                                .eq(CreditModuleEntity::getPlanId, planEntity.getId()))
                        .forEach(m -> {
                            m.setPlanId(plan.getId());
                            //进行修改
                            creditModuleDao.updateById(m);
                        });
            }

            // 在启用改方案之后，需要设置子类的父级Id
            baseMapper.update(new PlanEntity(), Wraps.lbU(new PlanEntity())
                    .eq(PlanEntity::getGrade, plan.getGrade())
                    .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_NOT.getEnable())
                    .eq(PlanEntity::getApplicationObject, plan.getApplicationObject())
                    .set(PlanEntity::getParentId, plan.getId()));
        }

        //进行当前的修改
        baseMapper.updateById(plan);
    }
}