package com.zzd.spring.rabbitmq.service;

import com.zzd.spring.rabbitmq.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MsgSender {
	private static final Logger logger = LoggerFactory.getLogger(MsgSender.class);
	@Autowired
	private AmqpTemplate rabbAmqpTemplate;

	/**
	 * 循环多次简单发送消息
	 * 
	 * @param msg
	 */
	public void sendMsgOneToMany(String msg) {
		logger.info("sendMsgOneToMany:" + msg);
		for (int i = 0; i < 20; i++) {
			sendMsg(msg + i);
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendTopic(String msg) {
		for (int i = 0; i < 10; i++) {
			sendTopic1(msg);
			sendTopicMessage(msg);
			sendTopicMessages(msg);
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendFanoutMany(String msg) {
		for (int i = 0; i < 20; i++) {
			sendFanout(msg + i);
		}
	}

	/**
	 * routingKey不管写什么都不会有影响
	 * 发送消息过后，FANOUTEXCHANGE绑定的都会接收到消息，
	 * @param msg
	 */
	public void sendFanout(String msg) {
		logger.info("sendFanout:" + msg);
		rabbAmqpTemplate.convertAndSend(Constant.FANOUTEXCHANGE, "routing key", msg);
	}

	/**
	 * 简单发送消息
	 * 
	 * @param msg
	 */
	public void sendMsg(String msg) {
		logger.info("Sender:" + msg);
		rabbAmqpTemplate.convertAndSend(Constant.HELLO, msg);
	}

	/**
	 * 只有receiver2才能接收到
	 * 
	 * @param msg
	 */
	public void sendTopic1(String msg) {
		msg = msg + "sendTopic1";
		logger.info("sendTopic1:" + msg);
		rabbAmqpTemplate.convertAndSend(Constant.TOPICEXCHANGE, "topic.1", msg);
	}

	/**
	 * receiver1,receiver2都会接受，是同时接收，不是负载均衡
	 * 
	 * @param msg
	 */
	public void sendTopicMessage(String msg) {
		msg = msg + "sendTopicMessage";
		logger.info("sendTopicMessage:" + msg);
		rabbAmqpTemplate.convertAndSend(Constant.TOPICEXCHANGE, "topic.message", msg);
	}

	/**
	 * 只有receiver2才会接收
	 * 
	 * @param msg
	 */
	public void sendTopicMessages(String msg) {
		msg = msg + "sendTopicMessages";
		logger.info("sendTopicMessages:" + msg);
		rabbAmqpTemplate.convertAndSend(Constant.TOPICEXCHANGE, "topic.messages", msg);
	}
}
