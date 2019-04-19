package com.zzd.spring.receiver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Created by Administrator on 2019/4/15 0015.
 */
@EnableBinding(MyTranceProcessor.class)
public class TransFormService {
    private final Logger log = LoggerFactory.getLogger(TransFormService.class);

    /**
     * 消息中转站处理方式
     *
     * @param payload
     * @return
     */
    @StreamListener(MyTranceProcessor.INPUT)
    @SendTo(MyTranceProcessor.OUTPUT)
    @ServiceActivator(inputChannel = MyTranceProcessor.INPUT,outputChannel = MyTranceProcessor.OUTPUT)
    public Object transform(Object payload) {
        log.info("消息中转站：{}", payload.toString());
        return payload;
    }
}
