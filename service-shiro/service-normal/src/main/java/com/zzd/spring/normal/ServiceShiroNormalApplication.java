package com.zzd.spring.normal;

import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.zzd.spring.normal" })
@ComponentScan(basePackages = {"com.zzd.spring.normal"})
@ServletComponentScan
public class ServiceShiroNormalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceShiroNormalApplication.class, args);
	}

}
