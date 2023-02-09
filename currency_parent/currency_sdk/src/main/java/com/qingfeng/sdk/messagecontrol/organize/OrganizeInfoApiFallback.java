package com.qingfeng.sdk.messagecontrol.organize;

import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/9
 */
public class OrganizeInfoApiFallback implements OrganizeInfoApi {

    /**
     * 删除视频信息
     * @param vodId
     * @return
     */
    @Override
    public R removeVod(String vodId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
