package com.qingfeng.sdk.planstandard.module;

import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/7
 */
public class CreditModuleApiFallback implements CreditModuleApi{

    @Override
    public R<List<CreditModuleEntity>> moduleListByStuId() {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
