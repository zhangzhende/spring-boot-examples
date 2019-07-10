package com.zzd.redis.controller;

import com.zzd.redis.service.MyCacheService;
import com.zzd.spring.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 说明类的用途
 * @ClassName CacheController
 * @Author zzd
 * @Create 2019/7/10 14:18
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/cache")
public class CacheController {
    @Autowired
    private MyCacheService cacheService;

    @RequestMapping(value = "/setvalue", method = RequestMethod.POST)
    public String setValue(@RequestParam(value = "userName") String userName,
                           @RequestParam(value = "passWord") String passWord) {
        User user = new User(userName, passWord);
        String key = cacheService.setValue(user);
        return key;
    }

    @RequestMapping(value = "/getvalue", method = RequestMethod.GET)
    public User getValue(@RequestParam(value = "key") String key) {
        User user = cacheService.getValue(key);
        return user;
    }

}
