package com.qingfeng.sdk.school.club;

import com.qingfeng.cms.domain.club.dto.ClubScoreModuleSaveDTO;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/15
 */
@FeignClient(value = "cms-school-report", fallback = ClubScoreModuleApiFallback.class)
@Component
public interface ClubScoreModuleApi {

    /**
     * 保存社团得分情况
     * @param clubScoreModuleSaveDTO
     * @return
     */
    @PostMapping("/club_score_module/save")
    public R save(@RequestBody ClubScoreModuleSaveDTO clubScoreModuleSaveDTO);
}
