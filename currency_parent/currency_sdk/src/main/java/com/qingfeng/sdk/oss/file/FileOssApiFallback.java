package com.qingfeng.sdk.oss.file;

import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/17
 */
public class FileOssApiFallback implements FileOssApi{

    @Override
    public R fileDelete(String fileUrl) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
