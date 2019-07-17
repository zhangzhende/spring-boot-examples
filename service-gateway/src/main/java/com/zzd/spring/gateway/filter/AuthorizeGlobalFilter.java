package com.zzd.spring.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局拦截，全局过滤器
 * Created by Administrator on 2019/4/4 0004.
 */
@Component
public class AuthorizeGlobalFilter implements GlobalFilter, Ordered {
    private static final Log logger = LogFactory.getLog(AuthorizeGlobalFilter.class);
    private static final String SYSNAME = "sysname";
    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //验证token
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(SYSNAME);
        if (StringUtils.isBlank(token)) {
            token = request.getQueryParams().getFirst(SYSNAME);
        }
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            logger.info("from global filer :SYSNAME: not exist");
            return response.setComplete();
        }

        String info = String.format("Method:{%s} Host:{%s} Path:{%s} Query:{%s}",
                exchange.getRequest().getMethod().name(),
                exchange.getRequest().getURI().getHost(),
                exchange.getRequest().getURI().getPath(),
                exchange.getRequest().getQueryParams());

        logger.info(info);

        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        //执行方法后记录本次方法调用的时间
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                Long executeTime = (System.currentTimeMillis() - startTime);
                logger.info(exchange.getRequest().getURI().getRawPath() + " : " + executeTime + "ms");
            }
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
