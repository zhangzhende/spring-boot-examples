package com.zzd.spring.oauth.service;

import com.zzd.spring.oauth.entity.SysAccount;
import com.zzd.spring.oauth.mapper.SysAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/4/12 0012.
 */
public class UserDetailServiceImpl implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Autowired
    private SysAccountMapper sysAccountMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysAccount user =sysAccountMapper.loadUserByUsername(username);
        if(user == null){
            logger.info("登录用户【"+username + "】不存在.");
            throw new UsernameNotFoundException("登录用户【"+username + "】不存在.");
        }
        return new User(user.getUsername(),user.getPassword(),getAuthority());
    }
    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
