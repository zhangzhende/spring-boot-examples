package com.zzd.demothree;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDistributedTransaction
@EnableFeignClients
public class ServiceTxlcnDemothreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTxlcnDemothreeApplication.class, args);
    }

}
