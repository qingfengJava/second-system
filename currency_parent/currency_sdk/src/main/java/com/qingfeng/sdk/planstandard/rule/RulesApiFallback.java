package com.qingfeng.sdk.planstandard.rule;

import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/8
 */
public class RulesApiFallback implements RulesApi {

    @Override
    public R<List<CreditRulesEntity>> ruleInfoByIds(List<Long> rulesIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
