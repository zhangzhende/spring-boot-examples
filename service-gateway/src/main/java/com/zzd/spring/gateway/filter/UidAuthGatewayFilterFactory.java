package com.zzd.spring.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.Arrays;
import java.util.List;

/**
 * 继承AbstractGatewayFilterFactory类实现过滤器
 */
public class UidAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<UidAuthGatewayFilterFactory.Config> {

    private static final Log logger = LogFactory.getLog(UidAuthGatewayFilterFactory.class);
    private static final String AUTH_UID= "uid";

    public UidAuthGatewayFilterFactory() {
        super(Config.class);
        logger.info("Loaded GatewayFilterFactory [Authorize]");
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(AUTH_UID);
            if (StringUtils.isBlank(token)) {
                token = request.getQueryParams().getFirst(AUTH_UID);
            }
            ServerHttpResponse response = exchange.getResponse();
            if (StringUtils.isBlank(token)) {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                logger.info("from custom filer :AUTH_UID: not exist");
                return response.setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    public static class Config {
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
