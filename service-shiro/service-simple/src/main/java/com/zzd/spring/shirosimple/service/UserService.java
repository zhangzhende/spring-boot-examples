package com.zzd.spring.shirosimple.service;

import com.zzd.spring.shirosimple.entity.User;
import com.zzd.spring.shirosimple.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2019/4/16 0016.
 */
@Service
public class UserService {

    /**
     *
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * SELECT p.pval FROM perm p, role_perm rp, user_role ur
     * WHERE p.pid = rp.perm_id AND ur.role_id = rp.role_id
     * AND ur.user_id = #{userId}
     *
     * @param uid
     * @return
     */
    public Set<String> getPermsByUserId(Long uid) {

//        return userMapper.getPermsByUserId(uid);
        Set<String> set=new HashSet<>();
        set.add("install");
        set.add("build");
        return set;
    }

    /**
     * 模拟根据用户id查询返回用户的所有角色，实际查询语句参考：
     * SELECT r.rval FROM role r, user_role ur
     * WHERE r.rid = ur.role_id AND ur.user_id = #{userId}
     *
     * @param userId
     * @return
     */
    public Set<String> getRolesByUserId(Long userId) {

//        return userMapper.getRolesByUserId(userId);
        Set<String> set=new HashSet<>();
        set.add("js");
        set.add("python");
        return set;
    }


    /**
     * 查询用户信息
     * @param name
     * @return
     */
    public User findUserByName(String name) {

//        return userMapper.findUserByName(name);
        User user = new User();
        user.setUname(name);
        user.setNick(name+"NICK");
        user.setPwd("J/ms7qTJtqmysekuY8/v1TAS+VKqXdH5sB7ulXZOWho=");//密码明文是123456
        user.setSalt("wxKYXuTPST5SG0jMQzVPsg==");//加密密码的盐值
        user.setUid(new Random().nextLong());//随机分配一个id
        user.setCreated(new Date());
        return user;
    }
}
