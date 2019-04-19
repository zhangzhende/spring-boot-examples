package com.zzd.spring.receiver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceStreamReceiver2Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceStreamReceiver2Application.class, args);
	}

}
