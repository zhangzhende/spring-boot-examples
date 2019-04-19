package com.zzd.spring.pub.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义一个分组输出通道
 * Created by Administrator on 2019/4/15 0015.
 */
public interface MyGroupOutput {
    String OUTPUT = "myGroupOutput";
    /**
     * 自定义消息通道，通道名称myOutput
     * @return
     */
    @Output(value = "myGroupOutput")
    MessageChannel myOutput();
}
