package com.qingfeng.sdk.active.sign;

import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/31
 */
public class ActiveSignApiFallback implements ActiveSignApi {

    @Override
    public R<List<ActiveSignEntity>> findByApplyIdsList(List<Long> applyIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
