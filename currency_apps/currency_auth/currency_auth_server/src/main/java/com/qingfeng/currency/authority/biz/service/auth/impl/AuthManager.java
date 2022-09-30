package com.qingfeng.currency.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qingfeng.currency.auth.server.utils.JwtTokenServerUtils;
import com.qingfeng.currency.auth.utils.JwtUserInfo;
import com.qingfeng.currency.auth.utils.Token;
import com.qingfeng.currency.authority.biz.service.auth.ResourceService;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.dto.auth.LoginDTO;
import com.qingfeng.currency.authority.dto.auth.ResourceQueryDTO;
import com.qingfeng.currency.authority.dto.auth.UserDTO;
import com.qingfeng.currency.authority.entity.auth.Resource;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.constant.CacheKey;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证管理器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
@Service
@Slf4j
public class AuthManager {

    @Autowired
    private UserService userService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CacheChannel cacheChannel;

    /**
     * 具体执行登录认证的逻辑
     *
     * @param account
     * @param password
     * @return
     */
    public R<LoginDTO> login(String account, String password) {
        //1、验证码校验通过，校验账号和密码是否正确
        R<User> result = this.checkUser(account, password);
        //判断成功还是失败
        if (result.getIsError()) {
            return R.fail(result.getCode(), result.getMsg());
        }
        User user = result.getData();
        //2、为用户生成JWT令牌
        Token token = this.generateUserToken(user);

        //查询当前用户可以访问的资源权限
        List<Resource> resourceList = resourceService.findVisibleResource(
                ResourceQueryDTO
                        .builder()
                        .userId(user.getId())
                        .build());
        log.info("当前用户拥有的资源权限为：{}", resourceList);
        List<String> permissionsList = null;
        if (resourceList != null && resourceList.size() > 0) {
            //3、将用户对应的权限（给前端使用的）进行缓存
            permissionsList = resourceList.stream()
                    .map(Resource::getCode).
                    collect(Collectors.toList());

            //4、将用户对应的权限（给后端网关使用的）进行缓存
            List<String> userResource = resourceList.stream()
                    .map(r -> {
                        return r.getMethod() + r.getUrl();
                    }).collect(Collectors.toList());
            //将当前用户可访问的资源载入缓存，形式为：GET/user/page
            cacheChannel.set(CacheKey.USER_RESOURCE,
                    user.getId().toString(),
                    userResource);
        }

        //5、封装返回结果
        LoginDTO loginDTO = LoginDTO.builder()
                .user(dozerUtils.map(user, UserDTO.class))
                .token(token)
                .permissionsList(permissionsList)
                .build();

        return R.success(loginDTO);
    }

    /**
     * 登录认证
     *
     * @param account
     * @param password
     * @return
     */
    private R<User> checkUser(String account, String password) {
        User user = userService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, account));

        //将前端提交的密码进行md5加密
        String md5Hex = DigestUtils.md5Hex(password);

        if (user == null || !user.getPassword().equals(md5Hex)) {
            //认证失败
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }

        return R.success(user);
    }

    /**
     * 为当前用户生成jwt token
     *
     * @param user
     * @return
     */
    private Token generateUserToken(User user) {
        //封装信息
        JwtUserInfo jwtUserInfo = JwtUserInfo.builder()
                .userId(user.getId())
                .account(user.getAccount())
                .name(user.getName())
                .orgId(user.getOrgId())
                .stationId(user.getStationId())
                .build();
        //根据用户信息生成token
        Token token = jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
        log.info("token={}", token.getToken());
        return token;
    }
}
