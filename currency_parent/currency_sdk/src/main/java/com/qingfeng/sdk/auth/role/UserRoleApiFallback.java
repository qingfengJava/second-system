package com.qingfeng.sdk.auth.role;

import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
public class UserRoleApiFallback implements UserRoleApi {

    @Override
    public R<UserRoleVo> findRoleIdByUserId(Long userId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<User> findRoleInfo() {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
