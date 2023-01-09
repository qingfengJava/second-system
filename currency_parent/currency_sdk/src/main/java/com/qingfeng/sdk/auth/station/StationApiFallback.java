package com.qingfeng.sdk.auth.station;

import com.qingfeng.currency.authority.entity.core.Station;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
public class StationApiFallback implements StationApi{


    @Override
    public R<List<Station>> findByOrgId(Long orgId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
