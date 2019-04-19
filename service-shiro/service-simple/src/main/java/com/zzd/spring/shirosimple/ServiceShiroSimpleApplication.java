package com.zzd.spring.shirosimple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(basePackages = {"com.zzd.spring.shirosimple" })
@EnableDiscoveryClient
public class ServiceShiroSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceShiroSimpleApplication.class, args);
	}

}
