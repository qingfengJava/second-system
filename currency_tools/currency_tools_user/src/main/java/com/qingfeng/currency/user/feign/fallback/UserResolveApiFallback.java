package com.qingfeng.currency.user.feign.fallback;

import com.qingfeng.currency.base.R;
import com.qingfeng.currency.user.feign.UserQuery;
import com.qingfeng.currency.user.feign.UserResolveApi;
import com.qingfeng.currency.user.model.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户API熔断
 *
 * @author 清风学Java
 */
@Component
@Slf4j
public class UserResolveApiFallback implements FallbackFactory<UserResolveApi> {


    @Override
    public UserResolveApi create(Throwable throwable) {
        return new UserResolveApi() {
            /**
             * 根据id 查询用户详情
             *
             * @param id
             * @param userQuery
             * @return
             */
            @Override
            public R<SysUser> getById(Long id, UserQuery userQuery) {
                log.error("通过用户名查询用户异常:{}", id, throwable);
                return R.timeout();
            }
        };
    }
}
