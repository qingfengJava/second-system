package com.qingfeng.sdk.school.club;

import com.qingfeng.cms.domain.club.dto.ClubScoreModuleSaveDTO;
import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/15
 */
public class ClubScoreModuleApiFallback implements ClubScoreModuleApi {

    @Override
    public R save(ClubScoreModuleSaveDTO clubScoreModuleSaveDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
