package com.zzd.spring.normal.service;

import com.zzd.spring.normal.entity.User;
import com.zzd.spring.normal.mapper.UserMapper;
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
     * 由用户名查询用户信息
     * @param name
     * @return
     */
    public User getUserByName(String name) {

//        return userMapper.findUserByName(name);

        return getUser(name);
    }

    /**
     * 由手机号查询用户信息
     * @param phone
     * @return
     */
    public User getUserByPhone(String phone) {

//        return userMapper.findUserByName(name);

        return getUser(phone);
    }

    /**
     * 由第三方openId查询
     * @param openId
     * @return
     */
    public User getUserByOpenId(String openId) {

//        return userMapper.findUserByName(name);

        return getUser(openId);
    }


    /**
     * 造假数据
     * @param str
     * @return
     */
    private User getUser(String str){
        User user = new User();
        user.setUname(str);
        user.setNick(str+"NICK");
        user.setPhone(str);
        user.setPwd("e10adc3949ba59abbe56e057f20f883e");//密码明文是123456
        user.setSalt("wxKYXuTPST5SG0jMQzVPsg==");//加密密码的盐值
        user.setUid(new Random().nextLong());//随机分配一个id
        user.setCreated(new Date());
        return user;
    }

}
