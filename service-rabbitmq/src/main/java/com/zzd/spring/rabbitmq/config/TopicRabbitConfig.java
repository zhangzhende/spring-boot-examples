package com.zzd.spring.rabbitmq.config;

import com.zzd.spring.rabbitmq.utils.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TopicRabbitConfig {
	/*
	 * topic 是RabbitMQ中最灵活的一种方式，可以根据routing_key自由的绑定不同的队列
	 */
	public static final String message = "topic.message";
	public static final String messages = "topic.messages";

	@Bean(name = "queueMessage")
	public Queue queueMessage() {
		return new Queue(TopicRabbitConfig.message);
	}

	@Bean(name = "queueMessages")
	public Queue queueMessages() {
		return new Queue(TopicRabbitConfig.messages);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(Constant.TOPICEXCHANGE);
	}

	/**
	 * 这个Queue的变量名称必须跟上面定义的beanname一致才行，不然报错
	 * 
	 * @param queueMessage
	 * @param exchange
	 * @return
	 */
	@Bean
	Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
		return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
	}

	@Bean
	Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
		return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
	}

}
