package com.zzd.spring.kafka;

import com.zzd.spring.kafka.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class ServiceKafkaApplication {
    @Autowired
    private KafkaSender sender;

    public static void main(String[] args) {
        SpringApplication.run(ServiceKafkaApplication.class, args);

    }

    @Scheduled(fixedRate = 1000 * 2)
    public void testKafka() {
        sender.sendMsg();
    }

//    public static void main(String[] args) {
//        String a="a";
//        final String b="a";
//        String c=a+"c";
//        String d=b+"c";
//        String e="a"+"c";
//        String g="ac";
//        String f=a+"c";
//        System.out.println();
//
//    }
}
