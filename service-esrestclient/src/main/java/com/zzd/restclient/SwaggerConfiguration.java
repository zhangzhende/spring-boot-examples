package com.zzd.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangzhende
 * @version 2018年11月6日
 * @see SwaggerConfiguration
 * @since
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    /**
     * 记录日志
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfiguration.class);
	
	/**
	 * 意义，目的和功能，以及被用到的地方<br>
	 */
    @Value("${swagger.host}")
	private String hostAndPort;
	/**
	 * Description: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @return Docket
	 * @see 
	 */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("api")
                .apiInfo(apiInfo())
                .host(hostAndPort)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zzd.restclient.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * swagger
     * @return ApiInfo
     * @see 
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("搜索服务-采用restClient方式")//大标题
                .termsOfServiceUrl("http://loaclhost:80")
                .version("1.0")//版本
                .build();
    }
    
}