package com.zzd.spring.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * zuulfilter,这里用于全局拦截
 * Created by Administrator on 2019/4/11 0011.
 */
public class GloblePreZuulfilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(GloblePreZuulfilter.class);
    private static final String AUTH_TOKEN = "token";

    //    pre ->routing -> post ,以上3个顺序出现异常时都可以触发error类型的filter
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    /**
     * order值越大，优先级越低,这里需要指出的是，对于同filterType 类型是这样，比如都是pre
     */
    public int filterOrder() {
        return 1;
    }

    @Override
    /**
     * 是否应该执行该过滤器，如果是false，则不执行该filter
     */
    public boolean shouldFilter() {
        return true;
    }

    @Override
//    全局过滤，看看header中是否包含token
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("send  {} request to {} ", request.getMethod(), request.getRequestURL().toString());

        String token = request.getHeader(AUTH_TOKEN);
        if (StringUtils.isBlank(AUTH_TOKEN)) {
            token = request.getParameter(AUTH_TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            logger.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            ctx.setResponseBody(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return null;
        }
        logger.info("access token ok");
        return null;
    }
}
