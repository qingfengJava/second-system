package com.qingfeng.sdk.messagecontrol.organize;

import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/9
 */
@FeignClient(value = "cms-message-control", fallback = OrganizeInfoApiFallback.class)
@Component
public interface OrganizeInfoApi {

    /**
     * 删除视频信息
     * @param vodId
     * @return
     */
    @DeleteMapping("/organizeinfo/vod/{vodId}")
    public R removeVod(@PathVariable("vodId") String vodId);
}
