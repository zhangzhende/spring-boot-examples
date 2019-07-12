package com.zzd.spring.demoone;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDistributedTransaction
@EnableFeignClients
@MapperScan(basePackages = {"com.zzd.spring.demoone" })
public class ServiceTxlcnDemooneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTxlcnDemooneApplication.class, args);
    }

}
