package com.qingfeng.currency.common.constant;

import cn.hutool.core.util.StrUtil;
import com.qingfeng.currency.utils.StrPool;

/**
 * 用于同一管理和生成缓存的region和key， 避免多个项目使用的key重复
 * @author 清风学Java
 */
public interface CacheKey {
    /**
     * 验证码 前缀
     * 完整key: captcha:{key} -> str
     */
    String CAPTCHA = "captcha";
    /**
     * 资源 前缀
     * 完整key: resource:{resourceId} -> obj
     */
    String RESOURCE = "resource";

    /**
     * 用户拥有的资源 前缀
     * 完整key: user_resource:{userId} -> [RESOURCE_ID, ...]
     */
    String USER_RESOURCE = "user_resource";
    /**
     * 所有需要鉴权的资源
     */
    String RESOURCE_NEED_TO_CHECK = "resource_need_to_check";

    /**
     * 构建key
     * @param args
     * @return
     */
    static String buildKey(Object... args) {
        if (args.length == 1) {
            return String.valueOf(args[0]);
        } else if (args.length > 0) {
            return StrUtil.join(StrPool.COLON, args);
        } else {
            return "";
        }
    }
}