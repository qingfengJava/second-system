package com.qingfeng.sdk.auth.optlog;

import com.qingfeng.currency.base.R;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.log.entity.OptLogDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/8
 */
@Slf4j
public class OptLogApiFallback implements OptLogApi {

    @Override
    public void save(OptLogDTO optLogDTO) {
        throw new BizException(R.HYSTRIX_ERROR_MESSAGE);
    }
}
