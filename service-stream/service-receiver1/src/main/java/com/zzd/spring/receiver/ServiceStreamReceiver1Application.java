package com.zzd.spring.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceStreamReceiver1Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceStreamReceiver1Application.class, args);
	}

}
