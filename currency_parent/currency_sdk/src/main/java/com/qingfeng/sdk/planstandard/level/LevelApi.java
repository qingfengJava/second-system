package com.qingfeng.sdk.planstandard.level;

import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.currency.base.R;
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
@FeignClient(value = "cms-plan-standard", fallback = LevelApiFallback.class)
@Component
public interface LevelApi {

    /**
     * 根据等级id集合查询等级信息
     * @param levelIds
     * @return
     */
    @PostMapping("/level/info/list")
    public R<List<LevelEntity>> levelInfoByIds(@RequestBody List<Long> levelIds);
}
