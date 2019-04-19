package com.zzd.spring.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	@KafkaListener(topics={"test"})
	public void consumer(ConsumerRecord<?, ?> consumer){
		logger.info("receive test"+consumer.toString());
	}
}
