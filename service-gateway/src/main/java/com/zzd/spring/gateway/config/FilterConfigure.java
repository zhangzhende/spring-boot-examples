package com.zzd.spring.gateway.config;

import com.zzd.spring.gateway.filter.AuthorizeGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2019/4/4 0004.
 */
public class FilterConfigure {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocator routeLocator;
        routeLocator = builder.routes()
                .route(r -> r.path("/mongoapi")
                        .filters(f -> f.filter(new AuthorizeGatewayFilter())).uri("lb://service-mongo").order(0)).build();
        return routeLocator;
    }

}
