package com.qingfeng.sdk.messagecontrol.dict;

import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/30
 */
@FeignClient(value = "cms-message-control", fallback = DictFallback.class)
@Component
public interface DictApi {

    /**
     * 查询所有的学院信息
     * @return
     */
    @GetMapping("/dict/find/department")
    public R<List<DictEntity>> findDepartment();
}
