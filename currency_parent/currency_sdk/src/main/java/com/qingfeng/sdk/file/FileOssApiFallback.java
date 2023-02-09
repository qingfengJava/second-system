package com.qingfeng.sdk.file;

import com.qingfeng.currency.base.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/9
 */
public class FileOssApiFallback implements FileOssApi {

    /**
     * 上传图片文件
     * @param file
     * @return
     */
    @Override
    public R<String> uploadImg(MultipartFile file) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
