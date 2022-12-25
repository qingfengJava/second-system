package com.qingfeng.sdk.messagecontrol.StuInfo;

import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/23
 */
public class StuInfoApiFallback implements StuInfoApi{

    /**
     * 根据学生用户Id查询学生用户信息详情信息
     * @param userId
     * @return
     */
    @Override
    public R<StuInfoEntity> info(Long userId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    /**
     * 保存用户信息实体
     * @param stuInfoSaveDTO
     * @return
     */
    @Override
    public R save(StuInfoSaveDTO stuInfoSaveDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
