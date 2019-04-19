package com.zzd.spring.gateway;

import com.zzd.spring.gateway.filter.AuthorizeGatewayFilter;
import com.zzd.spring.gateway.filter.AuthorizeGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGatewayApplication.class, args);
    }

    /**
     * 注入实现接口的方式注入的特定filter
     * 对于同一个serivceid两个filter无法共存
     * 而且这个只能在代码里面，并且在配置文件里面指定的话并没有用
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service-es", r -> r.path("/esapi/**")
                        .filters(f -> f.stripPrefix(1).filter(new AuthorizeGatewayFilter()))
                        .uri("lb://service-es"))
                .build();
    }

    /**
     * 注入继承方式实现的filter
     *
     * @return
     */
    @Bean
    public AuthorizeGatewayFilterFactory AuthorizeGatewayFilterFactory() {
        return new AuthorizeGatewayFilterFactory();
    }
}
