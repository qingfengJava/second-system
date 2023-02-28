package com.qingfeng.sdk.messagecontrol.organize;

import com.qingfeng.cms.domain.organize.dto.OrganizeImgSaveDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/13
 */
@FeignClient(value = "cms-message-control", fallback = OrganizeImgApiFallback.class)
@Component
public interface OrganizeImgApi {

    /**
     * 保存社团设置的图片信息
     * @param organizeImgSaveDTO
     * @return
     */
    @PostMapping("/organizeimg/save")
    public R save(@RequestBody @Validated OrganizeImgSaveDTO organizeImgSaveDTO);

    /**
     * 根据社团Ids查询社团设置的图片
     * @param organizeIds
     * @return
     */
    @PostMapping("/organizeimg/list/img")
    public R<Map<Long, List<OrganizeImgEntity>>> listImg(@RequestBody List<Long> organizeIds);
}
