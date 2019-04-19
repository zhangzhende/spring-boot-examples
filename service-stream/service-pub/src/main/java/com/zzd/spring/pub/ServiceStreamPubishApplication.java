package com.zzd.spring.pub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceStreamPubishApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceStreamPubishApplication.class, args);
	}

}
