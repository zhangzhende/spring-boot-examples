package com.zzd.spring.api.config;

import feign.Retryer;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/4/11 0011.
 */
@Configuration
public class FeignConfig {
    public Retryer feignRetryer(){
        return new Retryer.Default(100,1000 * 60,5);
    }

}
