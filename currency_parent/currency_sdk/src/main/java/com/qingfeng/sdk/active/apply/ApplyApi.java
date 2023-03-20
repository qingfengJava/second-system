package com.qingfeng.sdk.active.apply;

import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/20
 */
@FeignClient(value = "cms-community-activities", fallback = ApplyApiFallback.class)
@Component
public interface ApplyApi {

    /**
     * 根据Id查询活动申请的详细信息
     * @param id
     * @return
     */
    @GetMapping("/apply/info/{id}")
    public R<ApplyEntity> info(@PathVariable("id") Long id);

    /**
     * 根据Ids查询活动集合
     * @param ids
     * @return
     */
    @PostMapping("/apply/info_list")
    public R<List<ApplyEntity>> infoListByIds(@RequestBody List<Long> ids);
}
