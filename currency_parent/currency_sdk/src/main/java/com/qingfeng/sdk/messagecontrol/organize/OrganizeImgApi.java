package com.qingfeng.sdk.messagecontrol.organize;

import com.qingfeng.cms.domain.organize.dto.OrganizeImgSaveDTO;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/save")
    public R save(@RequestBody @Validated OrganizeImgSaveDTO organizeImgSaveDTO);
}
