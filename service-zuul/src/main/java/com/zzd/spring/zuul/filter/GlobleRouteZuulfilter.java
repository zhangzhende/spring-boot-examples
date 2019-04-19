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
import java.time.Instant;

/**
 * Created by Administrator on 2019/4/12 0012.
 */
public class GlobleRouteZuulfilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(GlobleRouteZuulfilter.class);
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
//        sendZuulResponse如果在前面设置了为false，也就是被拦截不通过，那么这个拦截器就不起作用
        return RequestContext.getCurrentContext().getRouteHost() != null
                && RequestContext.getCurrentContext().sendZuulResponse();
    }

    @Override
    public Object run() throws ZuulException {
        long starttime = Instant.now().toEpochMilli();
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();


        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(HttpStatus.OK.value());
        long endtime = Instant.now().toEpochMilli();
        logger.info("route filter runtime  is {}", endtime - starttime);
        return null;
    }
}
