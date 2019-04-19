package com.zzd.spring.receiver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by Administrator on 2019/4/15 0015.
 */
//消息接受端，MyInput绑定input的，要跟我们配置文件的input关联的。
@EnableBinding(MyInput.class)
public class MyReceiveService {
    private final Logger log = LoggerFactory.getLogger(MyReceiveService.class);

    /**
     * 接收消息处理
     * @param payload
     */
    @StreamListener(MyInput.INPUT)
    public void receive(Object payload) {

        log.info(payload.toString());
    }
}
