package com.qingfeng.config;

import com.qingfeng.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 配置Shiro
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/1/13
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置shiro的cookieRememberMeManager功能
     * @return
     */
    @Bean
    public CookieRememberMeManager getCookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //给cookieRememberMeManager设置cookie    cookie必须设置名字
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //设置cookie的存活时间
        simpleCookie.setMaxAge(30*24*60*60);
        cookieRememberMeManager.setCookie(simpleCookie);

        return cookieRememberMeManager;
    }

    /**
     * 配置Shiro对缓存管理的支持
     * @return
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        //设置缓存管理的策略
        ehCacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
        return ehCacheManager;
    }

    /**
     * 配置SpringBoot对注解的支持
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    /**
     * 配置SpringBoot对注解的支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 过滤器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //过滤器就是shiro进行权限校验的核心，进行认证和授权是需要securityManager的
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //配置过滤器拦截规则
        HashMap<String, String> filterMap = new HashMap<>(10);
        /**
         * anon     表示未认证可访问的url
         * user     表示记住我可以访问的url (已认证也可以访问)
         * authc    表示已认证可以访问的url
         * perms    表示必须具备指定的权限才可访问
         * logout	表示指定退出的url
         */

        //拦截的资源
        //设置退出登录状态，实际上就是subject退出  这是shiro内部自己定义的规则
        filterMap.put("/exit","logout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置未授权访问的页面路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/lesspermission.html");

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义session管理器
     *
     * 注意：shiro内部是有默认的session管理的，如果我们需要对session进
     * 行管理，就需要自定session管理
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager getDefaultWevSessionsManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //配置sessionManager

        //设置session过期时间   单位是毫秒
        sessionManager.setGlobalSessionTimeout(15 * 1000);
        return sessionManager;
    }

    /**
     * SecurityManager
     * @param myRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyRealm myRealm, EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        //给安全管理器设置缓存管理
        defaultWebSecurityManager.setCacheManager(ehCacheManager);
        //将自定义的session管理器设置给securityManager
        defaultWebSecurityManager.setSessionManager(getDefaultWevSessionsManager());
        //配置cookieRememberMeManager
        defaultWebSecurityManager.setRememberMeManager(getCookieRememberMeManager());

        return defaultWebSecurityManager;
    }

    /**
     * 使用自定义Realm
     */
    @Bean
    public MyRealm getMyRealm(HashedCredentialsMatcher matcher) {
        MyRealm myRealm = new MyRealm();
        //给realm设置加密匹配器用户在认证的时候对密码进行加密校验
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

    /**
     * 配置加密匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // matcher就是用来指定加密规则   加密方式
        matcher.setHashAlgorithmName("md5");
        // 指定hash加密循环的次数
        matcher.setHashIterations(3);
        return matcher;
    }
}
