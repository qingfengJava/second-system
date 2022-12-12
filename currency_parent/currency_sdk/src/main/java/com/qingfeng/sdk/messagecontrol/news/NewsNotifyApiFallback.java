package com.qingfeng.sdk.messagecontrol.news;

import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/12
 */
public class NewsNotifyApiFallback implements NewsNotifyApi {

    @Override
    public R save(NewsNotifySaveDTO newsNotifySaveDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
