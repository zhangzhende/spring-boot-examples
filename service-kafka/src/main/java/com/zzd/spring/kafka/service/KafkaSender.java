package com.zzd.spring.kafka.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class KafkaSender {
	private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
	@Autowired
	private KafkaTemplate<String ,Object> kafkaTemplate;
	
	public void sendMsg(){
		logger.info("sending msg");
		kafkaTemplate.send("test", "hello kafka"+new Date().getTime());
	}
}
