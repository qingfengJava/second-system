package com.qingfeng.sdk.active.apply;

import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/20
 */
public class ApplyApiFallback implements ApplyApi {
    @Override
    public R<ApplyEntity> info(Long id) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<List<ApplyEntity>> infoListByIds(List<Long> ids) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
