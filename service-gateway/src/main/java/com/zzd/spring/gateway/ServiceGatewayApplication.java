package com.zzd.spring.gateway;

import com.zzd.spring.gateway.filter.TokenAuthGatewayFilter;
import com.zzd.spring.gateway.filter.TokenAuthGatewayFilterFactory;
import com.zzd.spring.gateway.filter.UidAuthGatewayFilterFactory;
import com.zzd.spring.gateway.filter.PlatformAuthGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
                        .filters(f -> f.stripPrefix(1).filter(new TokenAuthGatewayFilter()))
                        .uri("lb://service-es"))
                .build();
    }

    /**
     * 注入继承方式实现的filter
     *
     * @return
     */
    @Bean
    public UidAuthGatewayFilterFactory AuthorizeGatewayFilterFactory() {
        return new UidAuthGatewayFilterFactory();
    }
    /**
     * 注入平台验证过滤器
     * @return
     */
    @Bean
    public PlatformAuthGatewayFilterFactory PlatformAuthGatewayFilterFactory(){
        return new PlatformAuthGatewayFilterFactory();
    }
@Bean
    public TokenAuthGatewayFilterFactory TokenAuthGatewayFilterFactory(){
        return new TokenAuthGatewayFilterFactory();
    }
}
