package com.qingfeng.sdk.file;

import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/9
 */
@FeignClient(value = "cms-file", fallback = FileOssApiFallback.class)
@Component
public interface FileOssApi {

    @PostMapping("/file/upload_img")
    public R<String> uploadImg(@RequestParam("file") MultipartFile file);

}
