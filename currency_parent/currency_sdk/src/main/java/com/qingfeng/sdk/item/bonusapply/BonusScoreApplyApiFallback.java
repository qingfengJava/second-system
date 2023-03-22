package com.qingfeng.sdk.item.bonusapply;

import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/22
 */
public class BonusScoreApplyApiFallback implements BonusScoreApplyApi {

    @Override
    public R<List<BonusScoreApplyEntity>> findByIds(List<Long> ids) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
