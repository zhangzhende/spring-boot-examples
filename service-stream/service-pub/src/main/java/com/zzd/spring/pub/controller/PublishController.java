package com.zzd.spring.pub.controller;

import com.zzd.spring.pub.config.MyGroupSendService;
import com.zzd.spring.pub.config.MySendService;
import com.zzd.spring.pub.config.MyTranceSendService;
import com.zzd.spring.pub.config.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/15 0015.
 */
@RestController
@RequestMapping(value = "/pub")
public class PublishController {

    @Autowired
    private SendService sendService;

    @Autowired
    private MySendService mySendService;

    @Autowired
    private MyTranceSendService myTranceSendService;

    @Autowired
    private MyGroupSendService myGroupSendService;

    /**
     * 默认消息发送
     *
     * @param message
     */
    @RequestMapping("/send/{message}")
    public void send(@PathVariable(value = "message") String message) {
        sendService.sendMsg(message);

    }

    /**
     * 自定义消息发送
     *
     * @param message
     */
    @RequestMapping("/mysend/{message}")
    public void mysend(@PathVariable(value = "message") String message) {
        mySendService.sendMsg(message);

    }

    /**
     * 带有中间站的消息发送，即A-->b-->c
     *
     * @param message
     */
    @RequestMapping("/mytrancesend/{message}")
    public void myTranceSend(@PathVariable(value = "message") String message) {
        myTranceSendService.sendMsg(message);
    }

    @RequestMapping("/mygroupsend/{message}")
    public void myGroupSend(@PathVariable(value = "message") String message) {
        myGroupSendService.sendMsg(message);
    }
}
