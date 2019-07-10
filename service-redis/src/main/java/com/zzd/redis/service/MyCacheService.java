package com.zzd.redis.service;

import com.alibaba.fastjson.JSONObject;
import com.zzd.redis.config.MyRedisClusterTemplate;
import com.zzd.redis.config.MyRedisSingleTemplate;
import com.zzd.spring.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Description 说明类的用途
 * @ClassName MyCacheService
 * @Author zzd
 * @Create 2019/7/10 14:18
 * @Version 1.0
 **/
@Service
public class MyCacheService {

    @Autowired
    private MyRedisSingleTemplate redisTemplate;

    /**
     * redis中塞入数据，返回key
     * @param user
     * @return
     */
    public String  setValue(User user){
        String uuid= UUID.randomUUID().toString();
        redisTemplate.setObject(uuid,user,100);
        return uuid;
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public User getValue(String key){
        if(!redisTemplate.exists(key.getBytes())){
            return null;
        }
        Object ob=redisTemplate.getObject(key);
        User user=JSONObject.parseObject(JSONObject.toJSONString(ob),User.class);
        return user;
    }
}
