package com.zzd.service.arcsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.zzd.service" })
public class ServiceArcsoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceArcsoftApplication.class, args);
    }

}
