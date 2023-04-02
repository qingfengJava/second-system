package com.qingfeng.sdk.messagecontrol.organize;

import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

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

    /**
     * 根据用户Id查询社团组织信息
     * @return
     */
    @Override
    public R<OrganizeInfoEntity> info() {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<OrganizeInfoEntity> info(Long userId) {
        return this.info();
    }

    @Override
    public R<List<OrganizeInfoEntity>> infoList(List<Long> userIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<OrganizeInfoEntity> findInfoByName(String orgName) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
