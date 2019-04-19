package com.zzd.spring.receiver2.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义一个分组通道
 * Created by Administrator on 2019/4/15 0015.
 */
public interface MyGroupInput {
    String INPUT = "myGroupIutput";
    @Input(value = "myGroupIutput")
    SubscribableChannel input();
}
