package com.qingfeng.sdk.messagecontrol.news;

import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/12
 */
@FeignClient(value = "cms-message-control", fallback = NewsNotifyApiFallback.class)
@Component
public interface NewsNotifyApi {

    /**
     * 保存消息通知信息
     * @param newsNotifySaveDTO
     * @return
     */
    @PostMapping("/newsnotify/save")
    public R save(@RequestBody NewsNotifySaveDTO newsNotifySaveDTO);
}
