package com.zzd.spring.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRabbitmqApplication.class, args);
	}

}
