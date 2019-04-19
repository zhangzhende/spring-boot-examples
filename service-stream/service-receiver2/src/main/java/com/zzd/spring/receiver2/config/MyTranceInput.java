package com.zzd.spring.receiver2.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义输入
 * Created by Administrator on 2019/4/15 0015.
 */
public interface MyTranceInput {
    String INPUT = "myTranceInput";
    @Input(value = "myTranceInput")
    SubscribableChannel input();
}
