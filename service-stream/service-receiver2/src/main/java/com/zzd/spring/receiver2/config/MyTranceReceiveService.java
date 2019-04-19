package com.zzd.spring.receiver2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Created by Administrator on 2019/4/15 0015.
 */
//消息接受端，MyTranceInput，要跟我们配置文件的input关联的。
@EnableBinding(MyTranceInput.class)
public class MyTranceReceiveService {
    private final Logger log = LoggerFactory.getLogger(MyTranceReceiveService.class);

    /**
     * 经中间站转发接收后的消息处理
     * @param payload
     */
    @StreamListener(MyTranceInput.INPUT)
    public void receive(Object payload) {

        log.info(payload.toString());
    }
}
