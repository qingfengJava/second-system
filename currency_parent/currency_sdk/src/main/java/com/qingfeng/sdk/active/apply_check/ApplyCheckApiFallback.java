package com.qingfeng.sdk.active.apply_check;

import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/31
 */
public class ApplyCheckApiFallback implements ApplyCheckApi {

    @Override
    public R<List<ApplyCheckEntity>> findByApplyIds(List<Long> applyIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
