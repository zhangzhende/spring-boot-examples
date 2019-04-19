package com.zzd.spring.pub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 自定义消息
 * Created by Administrator on 2019/4/15 0015.
 */
//output和input,这和配置文件中的output，input对应的。
@EnableBinding(MyGroupOutput.class)
public class MyGroupSendService {
    @Autowired
    private MyGroupOutput mysource;

    public void sendMsg(String msg) {

        mysource.myOutput().send(MessageBuilder.withPayload(msg).build());
    }

}
