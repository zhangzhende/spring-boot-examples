package com.zzd.spring.oauth.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源认证配置
 * Created by Administrator on 2019/4/12 0012.
 */
@Configuration
@EnableResourceServer//注册开启资源服务器
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {



    private static final String RESOURCE_ID = "resource_id";
    @Autowired
    private DefaultTokenServices tokenServices;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private PermitAuthenticationFilter permitAuthenticationFilter;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true).tokenServices(tokenServices);
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 配置那些资源需要保护的
        http.requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())  //权限认证失败业务处理
                .authenticationEntryPoint(customAuthenticationEntryPoint());  //认证失败的业务处理
        http.addFilterBefore(permitAuthenticationFilter,AbstractPreAuthenticatedProcessingFilter.class); //自定义token过滤 token校验失败后自定义返回数据格式

    }
//    @Bean
//    public LogoutSuccessHandler customLogoutSuccessHandler(){
//        return new CustomLogoutSuccessHandler();
//    }


    @Bean
    public AuthenticationFailureHandler customLoginFailHandler(){
        return new CustomAuthenticationFailureHandler();
    }


    @Bean
    public OAuth2AuthenticationEntryPoint customAuthenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public OAuth2AccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }


    /**
     * 重写 token 验证失败后自定义返回数据格式
     * @return
     */
    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity translate(Exception e) throws Exception {
                ResponseEntity responseEntity = super.translate(e);
                OAuth2Exception body = (OAuth2Exception) responseEntity.getBody();
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                // do something with header or response
                if(401==responseEntity.getStatusCode().value()){
                    //自定义返回数据格式
                    Map<String,String> map =  new HashMap<>();
                    map.put("status","401");
                    map.put("message",e.getMessage());
                    map.put("timestamp", String.valueOf(LocalDateTime.now()));
                    return new ResponseEntity(JSON.toJSONString(map), headers, responseEntity.getStatusCode());
                }else{
                    return new ResponseEntity(body, headers, responseEntity.getStatusCode());
                }
            }
        };
    }

}
