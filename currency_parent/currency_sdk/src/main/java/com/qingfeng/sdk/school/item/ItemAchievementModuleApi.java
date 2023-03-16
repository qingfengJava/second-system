package com.qingfeng.sdk.school.item;

import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleSaveDTO;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleUpdateDTO;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/15
 */
@FeignClient(value = "cms-school-report", fallback = ItemAchievementModuleApiFallback.class)
@Component
public interface ItemAchievementModuleApi {

    /**
     * 保存方案模块得分情况
     * @param itemAchievementModuleSaveDTO
     * @return
     */
    @PostMapping("/item_achievement_module/save")
    public R save(@RequestBody ItemAchievementModuleSaveDTO itemAchievementModuleSaveDTO);

    /**
     * 误判，取消方案模块加分
     * @param itemAchievementModuleUpdateDTO
     * @return
     */
    @PutMapping
    public R cancelBonusPoints(@RequestBody ItemAchievementModuleUpdateDTO itemAchievementModuleUpdateDTO);
}
