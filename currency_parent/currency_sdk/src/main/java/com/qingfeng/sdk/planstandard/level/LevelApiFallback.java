package com.qingfeng.sdk.planstandard.level;

import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/8
 */
public class LevelApiFallback implements LevelApi {

    @Override
    public R<List<LevelEntity>> levelInfoByIds(List<Long> levelIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<LevelEntity> levelInfoById(Long levelId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
