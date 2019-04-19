package com.zzd.spring.rabbitmq.service;

import com.zzd.spring.rabbitmq.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = Constant.HELLO)
public class MsgReceiver {
	private static final Logger logger = LoggerFactory.getLogger(MsgReceiver.class);

	@RabbitHandler
	public void process1(String msg) {
		logger.info("Receiver1 get the msg:" + msg);
	}
}
