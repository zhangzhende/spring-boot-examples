package com.zzd.spring.shirosimple.config;

import com.zzd.spring.shirosimple.entity.User;
import com.zzd.spring.shirosimple.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by Administrator on 2019/4/16 0016.
 */
public class CustomRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(CustomRealm.class);
    @Autowired
    private UserService userService;

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * 定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        User user = (User) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        logger.info("获取角色信息：" + user.getRoles());
        logger.info("获取权限信息：" + user.getPerms());
        authorizationInfo.setRoles(user.getRoles());
        authorizationInfo.setStringPermissions(user.getPerms());
        return authorizationInfo;
    }

    /**
     * 定义如何获取用户信息的业务逻辑，给shiro做登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username=token.getUsername();
        if(username==null){
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        User user=userService.findUserByName(username);
        if(null==user){
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        Set<String> roles=userService.getRolesByUserId(user.getUid());
        Set<String> perms=userService.getPermsByUserId(user.getUid());
        user.getRoles().addAll(roles);
        user.getPerms().addAll(perms);

        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPwd(),user.getUname());
        if(null!=user.getSalt()){
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        }
        return info;
    }
}














































