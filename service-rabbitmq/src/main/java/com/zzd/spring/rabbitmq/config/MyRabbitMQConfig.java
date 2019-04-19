package com.zzd.spring.rabbitmq.config;

import com.zzd.spring.rabbitmq.utils.Constant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyRabbitMQConfig {

	@Bean
	public Queue Queue(){
		return new Queue(Constant.HELLO);
	}
}
