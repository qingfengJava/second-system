package com.qingfeng.cms.biz.statistics.service;

import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.domain.statistics.vo.StuSemesterCreditsVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/28
 */
@Service
public class StatisticsService {

    @Autowired
    private ClubScoreModuleService clubScoreModuleService;
    @Autowired
    private ItemAchievementModuleService itemAchievementModuleService;

    @Autowired
    private StuInfoApi stuInfoApi;


    /**
     * 学生学期学分修读情况
     * @param userId
     * @return
     */
    public List<StuSemesterCreditsVo> stuSemesterCredits(Long userId) {
        // 查询学生基本信息
        StuInfoEntity stuInfo = stuInfoApi.info(userId).getData();
        // 根据年级封装学期信息
        List<String> schoolYear = this.getSchoolYear(stuInfo);
        // 查询各个模块下的得分情况

        return null;
    }

    private List<String> getSchoolYear(StuInfoEntity stuInfo){
        int year = Integer.parseInt(stuInfo.getGrade().substring(0, 4));
        List<String> schoolYearList = new ArrayList<>(10);
        for (int i = 0; i < stuInfo.getEducationalSystem().getVariable(); i++) {
            schoolYearList.add(year+"-"+(year + 1) +"  第一学期");
            schoolYearList.add(year+"-"+(year + 1) +"  第二学期");
        }

        return schoolYearList;
    }
}
