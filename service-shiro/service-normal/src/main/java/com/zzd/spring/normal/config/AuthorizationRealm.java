package com.zzd.spring.normal.config;


import com.zzd.spring.normal.entity.User;
import com.zzd.spring.normal.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 统一角色授权控制策略
 */
public class AuthorizationRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationRealm.class);
    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = new User();
//        org.springframework.beans.BeanUtils和org.apache.commons.beanutils.BeanUtils两个的copyProperties参数时反的
//        source标识被拷贝的，有数据的
//        target标识复制后的，复制之前没数据的
        BeanUtils.copyProperties(principal,user);
        Set<String> roleSet = userService.getRolesByUserId(user.getUid());
        info.addRoles(roleSet);
        Set<String> permsSet = userService.getPermsByUserId(user.getUid());
        info.addStringPermissions(permsSet);
        log.info("---------------- 获取到以下权限 ----------------");
        log.info(info.getStringPermissions().toString());
        log.info("---------------- Shiro 权限获取成功 ----------------------");
        return info;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        return null;
    }
}
