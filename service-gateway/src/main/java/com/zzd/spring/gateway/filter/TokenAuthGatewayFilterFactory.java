package com.zzd.spring.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TokenAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenAuthGatewayFilterFactory.Config> {

    private static final Log logger = LogFactory.getLog(TokenAuthGatewayFilterFactory.class);
    private static final String TOKEN= "token";

    public TokenAuthGatewayFilterFactory() {
        super(Config.class);
        logger.info("Loaded GatewayFilterFactory [Authorize]");
    }



    @Override
    public GatewayFilter apply(Config config) {
        if(!config.isEnabled()){
//            return new GatewayFilter(){//非lambda表达式的方式
//                @Override
//                public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//                    return chain.filter(exchange);
//                }
//            };
            return (exchange, chain) -> {
                return chain.filter(exchange);};
        }else{
        return new TokenAuthGatewayFilter();
        }
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
