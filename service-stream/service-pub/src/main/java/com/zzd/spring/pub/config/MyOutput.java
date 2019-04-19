package com.zzd.spring.pub.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by Administrator on 2019/4/15 0015.
 */
public interface MyOutput {
    /**
     * 自定义消息通道，通道名称myOutput
     * @return
     */
    @Output(value = "myOutput")
    MessageChannel myOutput();
}
