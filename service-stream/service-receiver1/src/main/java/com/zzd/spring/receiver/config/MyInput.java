package com.zzd.spring.receiver.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义输入
 * Created by Administrator on 2019/4/15 0015.
 */
public interface MyInput {
    String INPUT = "myInput";
    @Input(value = "myInput")
    SubscribableChannel input();
}
