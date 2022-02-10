package com.qingfeng.realm;

import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 *
 * 规范：
 *  1、创建一个类继承AuthorizingRealm类（实现了Realm接口的类）
 *  2、重写doGetAuthorizationInfo和doGetAuthenticationInfo方法
 *  3、重写getName方法
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/1/13
 */
public class MyRealm extends AuthorizingRealm {

    /*@Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;*/

    /**
     * 获取授权数据（将当前用户的角色及权限信息查询出来，设置给securityManager）
     * @param principals
     * @return
     */
    @SneakyThrows
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前用户的用户名  之所以拿到用户名是因为在认证的时候第一个参数是username
        /*String userName = (String) principals.iterator().next();
        //根据用户名查询当前用户的角色列表
        Set<String> roleNames = roleDao.queryRoleNamesByUsername(userName);
        //根据用户名查询当前用户的权限列表
        Set<String> permissions = permissionDao.queryPermissionByUsername(userName);

        System.out.println("----------------查询角色权限信息");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //给当前用户设置角色
        simpleAuthorizationInfo.setRoles(roleNames);
        //给当前用户设置权限
        simpleAuthorizationInfo.setStringPermissions(permissions);

        //返回当前用户的角色和权限信息
        return simpleAuthorizationInfo;*/
        return null;
    }

    /**
     * 获取认证的安全数据（从数据库查询的用户的正确数据）
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //参数authenticationToken就是传递的subject.login(token)
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从token中获取用户名
        String username = token.getUsername();

        //根据用户名，从数据库查询当前用户的安全数据
//        User user = userDao.queryUserByUsername(username);
        //如果数据库中用户的密码是加了盐的，则需要传入盐
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getUserPwd(), ByteSource.Util.bytes(user.getPwdSalt()), getName());


//        return simpleAuthenticationInfo;
        return null;
    }

    /**
     * 重写getName方法，就是Realm的一个名字标识而已
     * @return
     */
    @Override
    public String getName() {
        return "myRealm";
    }
}
