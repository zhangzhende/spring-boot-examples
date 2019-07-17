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
 *通过继承的方式，实现过滤器逻辑
 */
public class PlatformAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<PlatformAuthGatewayFilterFactory.Config> {

    private static final Log logger = LogFactory.getLog(PlatformAuthGatewayFilterFactory.class);
    private static final String PLATFORM= "platform";

    public PlatformAuthGatewayFilterFactory() {
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
            String token = headers.getFirst(PLATFORM);
            if (StringUtils.isBlank(token)) {
                token = request.getQueryParams().getFirst(PLATFORM);
            }
            ServerHttpResponse response = exchange.getResponse();
            if (StringUtils.isBlank(token)) {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                logger.info("from custom filer :PLATFORM: not exist");
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
