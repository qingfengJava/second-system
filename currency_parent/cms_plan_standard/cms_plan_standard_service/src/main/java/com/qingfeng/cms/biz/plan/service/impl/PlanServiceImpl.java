package com.qingfeng.cms.biz.plan.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.level.dao.LevelDao;
import com.qingfeng.cms.biz.module.dao.CreditModuleDao;
import com.qingfeng.cms.biz.plan.dao.PlanDao;
import com.qingfeng.cms.biz.plan.enums.PlanExceptionMsg;
import com.qingfeng.cms.biz.plan.enums.PlanIsEnable;
import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.biz.project.dao.ProjectDao;
import com.qingfeng.cms.biz.project.enums.ProjectEnable;
import com.qingfeng.cms.biz.rule.dao.CreditRulesDao;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
import com.qingfeng.cms.domain.level.vo.LevelListVo;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.vo.CreditModuleVo;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.vo.PlanEntityVo;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.rule.vo.CreditRulesVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.student.enums.StudentTypeEnum;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


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
    private DozerUtils dozerUtils;
    @Autowired
    private CreditModuleDao creditModuleDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private LevelDao levelDao;
    @Autowired
    private CreditRulesDao creditRulesDao;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private StuInfoApi stuInfoApi;

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
            if (baseMapper.selectById(plan.getId()).getIsEnable().equals(PlanIsEnable.ENABLE_TURE.getEnable())) {
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

    @Override
    @Transactional(rollbackFor = BizException.class)
    public PlanEntityVo getPlan(Long userId) {
        //首先要判断用户是不是学生，只有学生可以查看
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.STUDENT.name()});
        if (userIdByCode.getData().contains(userId)) {
            //说明是学生  查询学生信息，找到对应的年级
            StuInfoEntity stuInfoEntity = stuInfoApi.info(userId).getData();
            AtomicReference<Integer> applicationObject = new AtomicReference<>(0);
            Optional.ofNullable(stuInfoEntity)
                    .ifPresent(s -> {
                        if (s.getType().equals(StudentTypeEnum.UNDERGRADUATE_FOR_FOUR_YEARS) ||
                                s.getType().equals(StudentTypeEnum.UNDERGRADUATE_FOR_FIVE_YEARS)) {
                            applicationObject.set(1);
                        } else if (s.getType().equals(StudentTypeEnum.SPECIALTY)) {
                            applicationObject.set(2);
                        } else if (s.getType().equals(StudentTypeEnum.GRADUATE_STUDENT)) {
                            applicationObject.set(3);
                        }
                    });
            //根据年级和本专科查询对应的方案信息
            PlanEntity planEntity = baseMapper.selectOne(Wraps.lbQ(new PlanEntity())
                    .likeLeft(PlanEntity::getGrade, stuInfoEntity.getGrade())
                    .eq(PlanEntity::getApplicationObject, applicationObject.get())
                    .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable()));

            PlanEntityVo planEntityVo = getPlanEntityVo(planEntity);

            return planEntityVo;

        } else {
            // TODO 新增班级可以查看的
        }
        return null;
    }

    /**
     * 根据方案Id查询方案详情
     * @param planId
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public PlanEntityVo getPlanInfo(Long planId) {
        //根据年级和本专科查询对应的方案信息
        PlanEntity planEntity = baseMapper.selectById(planId);

        PlanEntityVo planEntityVo = getPlanEntityVo(planEntity);

        return planEntityVo;
    }

    @Override
    public PlanEntity getPlanByUserId(Long userId) {
        //首先要判断用户是不是学生，只有学生可以查看
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.STUDENT.name()});
        if (userIdByCode.getData().contains(userId)) {
            //说明是学生  查询学生信息，找到对应的年级
            StuInfoEntity stuInfoEntity = stuInfoApi.info(userId).getData();
            AtomicReference<Integer> applicationObject = new AtomicReference<>(0);
            Optional.ofNullable(stuInfoEntity)
                    .ifPresent(s -> {
                        if (s.getType().equals(StudentTypeEnum.UNDERGRADUATE_FOR_FOUR_YEARS) ||
                                s.getType().equals(StudentTypeEnum.UNDERGRADUATE_FOR_FIVE_YEARS)) {
                            applicationObject.set(1);
                        } else if (s.getType().equals(StudentTypeEnum.SPECIALTY)) {
                            applicationObject.set(2);
                        } else if (s.getType().equals(StudentTypeEnum.GRADUATE_STUDENT)) {
                            applicationObject.set(3);
                        }
                    });
            //根据年级和本专科查询对应的方案信息
            return baseMapper.selectOne(Wraps.lbQ(new PlanEntity())
                    .likeLeft(PlanEntity::getGrade, stuInfoEntity.getGrade())
                    .eq(PlanEntity::getApplicationObject, applicationObject.get())
                    .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable()));

        }

        return null;
    }

    private PlanEntityVo getPlanEntityVo(PlanEntity planEntity) {
        PlanEntityVo planEntityVo = dozerUtils.map2(planEntity, PlanEntityVo.class);
        //封装方案下的模块信息
        List<CreditModuleEntity> creditModuleEntities = creditModuleDao.selectList(Wraps.lbQ(new CreditModuleEntity())
                .eq(CreditModuleEntity::getPlanId, planEntityVo.getId())
                .orderByAsc(CreditModuleEntity::getModuleContent));
        List<CreditModuleVo> creditModuleVoList = dozerUtils.mapList(creditModuleEntities, CreditModuleVo.class);
        //先查询所有模块下关联的所有项目，并根据模块Id进行map分组
        List<ProjectEntity> projectEntities = projectDao.selectList(Wraps.lbQ(new ProjectEntity())
                .in(ProjectEntity::getModuleId, creditModuleVoList.stream()
                        .map(CreditModuleVo::getId)
                        .collect(Collectors.toList()))
                .eq(ProjectEntity::getIsEnable, ProjectEnable.ENABLE_TURE.getEnable())
                .eq(ProjectEntity::getIsCheck, ProjectCheckEnum.IS_FINISHED)
                .orderByAsc(ProjectEntity::getId));
        Map<Long, List<ProjectListVo>> projectVoMap = dozerUtils.mapList(projectEntities, ProjectListVo.class)
                .stream()
                .collect(Collectors.groupingBy(ProjectListVo::getModuleId));
        //根据所有项目查询其关联的项目等级，根据项目Id进行分组
        List<LevelEntity> levelEntityList = levelDao.selectList(Wraps.lbQ(new LevelEntity())
                .in(LevelEntity::getProjectId, projectEntities.stream()
                        .map(ProjectEntity::getId)
                        .collect(Collectors.toList()))
                .eq(LevelEntity::getIsCheck, LevelCheckEnum.IS_FINISHED));
        Map<Long, List<LevelListVo>> levelListVoMap = dozerUtils.mapList(levelEntityList, LevelListVo.class)
                .stream()
                .collect(Collectors.groupingBy(LevelListVo::getProjectId));
        //根据项目等级，查询其关联的所有学分细则
        List<CreditRulesEntity> creditRulesEntityList = creditRulesDao.selectList(Wraps.lbQ(new CreditRulesEntity())
                .in(CreditRulesEntity::getLevelId, levelEntityList.stream()
                        .map(LevelEntity::getId)
                        .collect(Collectors.toList()))
                .eq(CreditRulesEntity::getIsCheck, LevelCheckEnum.IS_FINISHED));
        Map<Long, List<CreditRulesVo>> creditRulesVoMap = dozerUtils.mapList(creditRulesEntityList, CreditRulesVo.class)
                .stream()
                .collect(Collectors.groupingBy(CreditRulesVo::getLevelId));

        //根据模块要查询对应的项目信息
        creditModuleVoList.forEach(c ->
                CompletableFuture.runAsync(() -> {
                    List<ProjectListVo> projectListVos = projectVoMap.get(c.getId());
                    //封装项目下的等级
                    projectListVos.forEach(p ->
                            CompletableFuture.runAsync(() -> {
                                List<LevelListVo> levelListVos = levelListVoMap.get(p.getId());
                                //封装等级下的学分细则
                                levelListVos.forEach(l ->
                                        CompletableFuture.runAsync(() -> {
                                            l.setCreditRulesVoList(creditRulesVoMap.get(l.getId()));
                                        })
                                );
                                p.setLevelListVo(levelListVos);
                            })
                    );

                    c.setProjectListVo(projectListVos);
                })
        );

        //封装模块
        planEntityVo.setCreditModuleVoList(creditModuleVoList);
        return planEntityVo;
    }
}