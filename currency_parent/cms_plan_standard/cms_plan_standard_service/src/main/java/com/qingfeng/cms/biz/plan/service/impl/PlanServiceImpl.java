package com.qingfeng.cms.biz.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.plan.dao.PlanDao;
import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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


}