package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.service.SystemService;
import com.qingfeng.utils.SchoolYearUtils;
import com.qingfeng.vo.ResultVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/12
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Override
    @Cacheable(value = "schoolYear", keyGenerator = "keyGenerator")
    public ResultVO getSchoolYear(String schoolYear) {
        List<String> schoolYearList = null;
        if (schoolYear == null || schoolYear.isEmpty()) {
            schoolYearList = SchoolYearUtils.getSchoolYear(Calendar.getInstance().get(Calendar.YEAR)-3);
        }else{
            schoolYearList = SchoolYearUtils.getSchoolYear(Integer.parseInt(schoolYear.substring(0, 4)));
        }
        return new ResultVO(ResStatus.OK, "success", schoolYearList);
    }
}
