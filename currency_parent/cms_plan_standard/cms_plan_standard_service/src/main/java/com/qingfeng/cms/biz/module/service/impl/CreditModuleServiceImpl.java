package com.qingfeng.cms.biz.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.biz.module.dao.CreditModuleDao;
import com.qingfeng.cms.biz.module.enums.CreditModuleServiceExceptionMsg;
import com.qingfeng.cms.biz.module.service.CreditModuleService;
import com.qingfeng.cms.biz.plan.enums.PlanIsEnable;
import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.biz.project.service.ProjectService;
import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.module.dto.CreditModuleQueryDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleSaveDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleUpdateDTO;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.ro.ModuleTreeRo;
import com.qingfeng.cms.domain.module.vo.CreditModuleVo;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.ro.PlanTreeRo;
import com.qingfeng.cms.domain.plan.vo.PlanVo;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.student.enums.StudentTypeEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.clazz.ClazzInfoApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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
    @Autowired
    private ProjectService projectService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private CreditRulesService creditRulesService;

    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private ClazzInfoApi clazzInfoApi;

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
                        .planModuleLabel(p.getGrade() + "（" +
                                (p.getApplicationObject() == 1 ? "本科" : "专科")
                                + "）" + p.getPlanName())
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

    /**
     * 删除学分认定模块  将关联的等级学分一并删除
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void deleteByIds(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
        // 根据模块Id查询项目信息
        List<ProjectEntity> projectList = projectService.list(Wraps.lbQ(new ProjectEntity())
                .in(ProjectEntity::getModuleId, ids));
        if (CollUtil.isNotEmpty(projectList)) {
            List<Long> projectIds = projectList.stream()
                    .map(ProjectEntity::getId)
                    .collect(Collectors.toList());
            projectService.removeByIds(projectIds);
            // 查询项目下的等级信息
            List<LevelEntity> levelList = levelService.list(Wraps.lbQ(new LevelEntity())
                    .in(LevelEntity::getProjectId, projectIds));
            if (CollUtil.isNotEmpty(levelList)) {
                List<Long> levelIds = levelList.stream()
                        .map(LevelEntity::getId)
                        .collect(Collectors.toList());
                levelService.removeByIds(levelIds);
                // 查询项目下的学分
                List<CreditRulesEntity> ruleList = creditRulesService.list(Wraps.lbQ(new CreditRulesEntity())
                        .in(CreditRulesEntity::getLevelId));
                if (CollUtil.isNotEmpty(ruleList)) {
                    creditRulesService.removeByIds(ruleList.stream()
                            .map(CreditRulesEntity::getId)
                            .collect(Collectors.toList()
                            )
                    );
                }
            }
        }
    }

    private void checkCreditModule(CreditModuleEntity creditModuleEntity) {
        //检查是否有重名的模块名出现
        LbqWrapper<CreditModuleEntity> wrapper = Wraps.lbQ(new CreditModuleEntity())
                .eq(CreditModuleEntity::getCode, creditModuleEntity.getCode())
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

    /**
     * 查询学生下的所属的方案模块集合
     * @param userId
     * @return
     */
    @Override
    public List<CreditModuleEntity> moduleListByStuId(Long userId) {
        // 根据学生年级的学历类型进行查询
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
        PlanEntity planEntity = planService.getOne(Wraps.lbQ(new PlanEntity())
                .likeLeft(PlanEntity::getGrade, stuInfoEntity.getGrade())
                .eq(PlanEntity::getApplicationObject, applicationObject.get())
                .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable()));

        return baseMapper.selectList(Wraps.lbQ(new CreditModuleEntity())
                .eq(CreditModuleEntity::getPlanId, planEntity.getId())
                .orderByAsc(CreditModuleEntity::getModuleContent));
    }

    @Override
    public List<CreditModuleEntity> moduleListByClazzId(Long userId) {
        //查询班级信息
        ClazzInfoEntity clazzInfo = clazzInfoApi.info().getData();
        if (ObjectUtil.isNotEmpty(clazzInfo)) {
            AtomicReference<Integer> applicationObject = new AtomicReference<>(0);
            Optional.ofNullable(clazzInfo)
                    .ifPresent(s -> {
                        if (s.getClazzType().equals(StudentTypeEnum.UNDERGRADUATE_FOR_FOUR_YEARS) ||
                                s.getClazzType().equals(StudentTypeEnum.UNDERGRADUATE_FOR_FIVE_YEARS)) {
                            applicationObject.set(1);
                        } else if (s.getClazzType().equals(StudentTypeEnum.SPECIALTY)) {
                            applicationObject.set(2);
                        } else if (s.getClazzType().equals(StudentTypeEnum.GRADUATE_STUDENT)) {
                            applicationObject.set(3);
                        }
                    });
            //根据年级和本专科查询对应的方案信息
            PlanEntity planEntity = planService.getOne(Wraps.lbQ(new PlanEntity())
                    .likeLeft(PlanEntity::getGrade, clazzInfo.getGrade())
                    .eq(PlanEntity::getApplicationObject, applicationObject.get())
                    .eq(PlanEntity::getIsEnable, PlanIsEnable.ENABLE_TURE.getEnable()));

            return baseMapper.selectList(Wraps.lbQ(new CreditModuleEntity())
                    .eq(CreditModuleEntity::getPlanId, planEntity.getId())
                    .orderByAsc(CreditModuleEntity::getModuleContent));
        }

        throw new BizException("班级信息未补全，请先去补全班级信息！");
    }
}