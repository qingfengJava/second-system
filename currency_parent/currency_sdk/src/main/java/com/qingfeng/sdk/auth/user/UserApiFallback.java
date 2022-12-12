package com.qingfeng.sdk.auth.user;

import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/12
 */
@Slf4j
public class UserApiFallback implements UserApi {

    @Override
    public R<User> get(Long id) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
