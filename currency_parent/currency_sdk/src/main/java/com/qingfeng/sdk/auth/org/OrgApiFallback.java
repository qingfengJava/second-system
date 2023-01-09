package com.qingfeng.sdk.auth.org;

import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
public class OrgApiFallback implements OrgApi{

    @Override
    public R getOrgByDictName(String dictName) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
