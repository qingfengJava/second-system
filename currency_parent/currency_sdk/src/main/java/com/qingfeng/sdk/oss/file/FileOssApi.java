package com.qingfeng.sdk.oss.file;

import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/17
 */
@FeignClient(value = "cms-file", fallback = FileOssApiFallback.class)
@Component
public interface FileOssApi {

    @DeleteMapping("/file/file_delete")
    public R fileDelete(@RequestParam("fileUrl") String fileUrl);
}
