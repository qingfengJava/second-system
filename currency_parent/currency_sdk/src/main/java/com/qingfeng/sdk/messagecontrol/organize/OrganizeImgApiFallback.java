package com.qingfeng.sdk.messagecontrol.organize;

import com.qingfeng.cms.domain.organize.dto.OrganizeImgSaveDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.currency.base.R;

import java.util.List;
import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/13
 */
public class OrganizeImgApiFallback implements OrganizeImgApi{

    /**
     * 保存社团图片信息
     *
     * @param organizeImgSaveDTO
     * @return
     */
    @Override
    public R save(OrganizeImgSaveDTO organizeImgSaveDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<Map<Long, List<OrganizeImgEntity>>> listImg(List<Long> organizeIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
