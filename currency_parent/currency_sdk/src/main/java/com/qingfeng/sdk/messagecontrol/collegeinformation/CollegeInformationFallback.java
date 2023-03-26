package com.qingfeng.sdk.messagecontrol.collegeinformation;

import com.qingfeng.cms.domain.college.dto.CollegeInformationSaveDTO;
import com.qingfeng.cms.domain.college.dto.CollegeInformationUpdateDTO;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/30
 */
public class CollegeInformationFallback implements CollegeInformationApi {

    @Override
    public R<CollegeInformationEntity> info(Long userId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R save(CollegeInformationSaveDTO collegeInformationSaveDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R update(CollegeInformationUpdateDTO collegeInformationUpdateDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<List<StuInfoEntity>> getUserInfoList(Long userId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<List<Long>> depInfo(String organizationCode) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
