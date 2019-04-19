package com.zzd.spring.rabbitmq.service;

import com.zzd.spring.rabbitmq.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = Constant.HELLO)
public class MsgReceiver2 {
	private static final Logger logger = LoggerFactory.getLogger(MsgReceiver2.class);

	@RabbitHandler
	public void process2(String msg) {
		logger.info("Receiver2 get the msg:" + msg);
	}
}
