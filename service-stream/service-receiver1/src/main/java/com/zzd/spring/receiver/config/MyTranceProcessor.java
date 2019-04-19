package com.zzd.spring.receiver.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 转发中间站配置，需要input，output对应
 * Created by Administrator on 2019/4/15 0015.
 */
public interface MyTranceProcessor {

    String INPUT = "myTranceInput";
    @Input(value = "myTranceInput")
    SubscribableChannel input();

    String OUTPUT = "myTranceOutput";
    /**
     * 自定义消息通道，myTranceOutput
     * @return
     */
    @Output(value = "myTranceOutput")
    MessageChannel myOutput();
}
