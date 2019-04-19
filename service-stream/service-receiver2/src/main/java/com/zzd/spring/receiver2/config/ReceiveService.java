package com.zzd.spring.receiver2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by Administrator on 2019/4/15 0015.
 */
//消息接受端，stream给我们提供了Sink,Sink源码里面是绑定input的，要跟我们配置文件的input关联的。
@EnableBinding(Sink.class)
public class ReceiveService {
    private final Logger log = LoggerFactory.getLogger(ReceiveService.class);

    @StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        log.info(payload.toString());
    }
}
