package com.qingfeng.sdk.auth.role;

import com.qingfeng.currency.base.R;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/7
 */
@Slf4j
public class RoleApiFallback  implements RoleApi{

    @Override
    public R<List<Long>> findUserIdByCode(String[] codes) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
