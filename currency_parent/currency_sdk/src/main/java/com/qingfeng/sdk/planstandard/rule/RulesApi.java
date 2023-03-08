package com.qingfeng.sdk.planstandard.rule;

import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.currency.base.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/8
 */
@FeignClient(value = "cms-plan-standard", fallback = RulesApiFallback.class)
@Component
public interface RulesApi {

    /**
     * 根据学分细则Id集合查询学分细则信息
     * @param rulesIds
     * @return
     */
    @ApiOperation(value = "", notes = "根据学分细则Id查询学分细则信息")
    @PostMapping("/creditrules/info/list")
    public R<List<CreditRulesEntity>> ruleInfoByIds(@RequestBody List<Long> rulesIds);
}
