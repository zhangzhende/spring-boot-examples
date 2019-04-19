package com.zzd.spring.rabbitmq.config;

import com.zzd.spring.rabbitmq.utils.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutRabbitConfig {
	/*
	 * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息， 绑定了这个交换机的所有队列都收到这个消息
	 */
	@Bean
	public Queue AMessage() {
		return new Queue("fanout.A");
	}

	@Bean
	public Queue BMessage() {
		return new Queue("fanout.B");
	}

	@Bean
	public Queue CMessage() {
		return new Queue("fanout.C");
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange(Constant.FANOUTEXCHANGE);
	}

	@Bean
	Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExcahge) {
		return BindingBuilder.bind(AMessage).to(fanoutExcahge);
	}

	@Bean
	Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExcahge) {
		return BindingBuilder.bind(BMessage).to(fanoutExcahge);
	}

	@Bean
	Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExcahge) {
		return BindingBuilder.bind(CMessage).to(fanoutExcahge);
	}

}
