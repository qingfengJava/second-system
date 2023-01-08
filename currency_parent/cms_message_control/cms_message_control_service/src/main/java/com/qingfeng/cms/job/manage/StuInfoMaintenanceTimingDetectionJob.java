package com.qingfeng.cms.job.manage;

import cn.hutool.core.util.ObjectUtil;
import com.qingfeng.cms.biz.manage.service.InfoManageService;
import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.manage.entity.InfoManageEntity;
import com.qingfeng.cms.domain.manage.enums.InfoTypeEnum;
import com.qingfeng.cms.domain.manage.enums.TypeStatusEnum;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.student.enums.IsChangeEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 每天监测学生信息维护的开启和结束任务
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/7
 */
@Component
@Slf4j
public class StuInfoMaintenanceTimingDetectionJob {

    @Autowired
    private InfoManageService infoManageService;
    @Autowired
    private StuInfoService stuInfoService;

    private static final String STU_TYPE_ALL = "ALL";

    /**
     * 每天0时 监控学生信息开启
     *
     * @return
     */
    @XxlJob(value = "stuInfoMaintenanceTimingDetectionOpen")
    public ReturnT stuInfoMaintenanceTimingDetectionOpen() {
        //先查询需要开启的任务  一般都是一个任务
        InfoManageEntity infoManageEntity = infoManageService.getOne(Wraps.lbQ(new InfoManageEntity())
                .leFooter(InfoManageEntity::getStartTime, LocalDate.now())
                .geHeader(InfoManageEntity::getEndTime, LocalDate.now())
                .eq(InfoManageEntity::getType, InfoTypeEnum.STUDENT)
                .eq(InfoManageEntity::getTypeStatus, TypeStatusEnum.INIT));

        if (ObjectUtil.isNotEmpty(infoManageEntity)) {
            infoManageEntity.setTypeStatus(TypeStatusEnum.PROCESS);
            boolean updateOpen = infoManageService.updateById(infoManageEntity);

            if (updateStuInfo(infoManageEntity, updateOpen, IsChangeEnum.TRUE)) {
                return ReturnT.SUCCESS;
            }
        }

        //调度结果
        return ReturnT.FAIL;
    }

    /**
     * 每天0时 监控学生信息关闭
     *
     * @return
     */
    @XxlJob(value = "stuInfoMaintenanceTimingDetectionClose")
    public ReturnT stuInfoMaintenanceTimingDetectionClose() {
        //先查询是否有需要关闭的任务  结束任务那一天的第59分59秒执行
        InfoManageEntity infoManageEntity = infoManageService.getOne(Wraps.lbQ(new InfoManageEntity())
                .eq(InfoManageEntity::getEndTime, LocalDate.now())
                .eq(InfoManageEntity::getType, InfoTypeEnum.STUDENT)
                .eq(InfoManageEntity::getTypeStatus, TypeStatusEnum.PROCESS));

        if (ObjectUtil.isNotEmpty(infoManageEntity)) {
            infoManageEntity.setTypeStatus(TypeStatusEnum.COMPLETED);
            boolean updateClose = infoManageService.updateById(infoManageEntity);

            if (updateStuInfo(infoManageEntity, updateClose, IsChangeEnum.FALSE)) {
                return ReturnT.SUCCESS;
            }

        }
        //调度结果
        return ReturnT.FAIL;
    }

    private boolean updateStuInfo(InfoManageEntity infoManageEntity, boolean updateClose, IsChangeEnum aFalse) {
        if (updateClose) {
            //修改所有学生信息为进入维护状态
            boolean update = false;
            if (infoManageEntity.getTypeGrade().equals(STU_TYPE_ALL)) {
                List<Long> stuInfoIds = stuInfoService.list()
                        .stream()
                        .map(StuInfoEntity::getId)
                        .collect(Collectors.toList());
                update = stuInfoService.update(StuInfoEntity.builder()
                                .isChange(aFalse)
                                .build(),
                        Wraps.lbQ(new StuInfoEntity())
                                .in(StuInfoEntity::getId, stuInfoIds));
            } else {
                update = stuInfoService.update(StuInfoEntity.builder()
                                .isChange(aFalse)
                                .build(),
                        Wraps.lbQ(new StuInfoEntity())
                                .in(StuInfoEntity::getGrade, infoManageEntity.getTypeGrade()));
            }

            if (update) {
                //调度结果
                return true;
            }
        }
        return false;
    }
}
