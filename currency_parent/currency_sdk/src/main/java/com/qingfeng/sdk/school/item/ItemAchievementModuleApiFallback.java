package com.qingfeng.sdk.school.item;

import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleSaveDTO;
import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/15
 */
public class ItemAchievementModuleApiFallback implements ItemAchievementModuleApi {

    @Override
    public R save(ItemAchievementModuleSaveDTO itemAchievementModuleSaveDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
