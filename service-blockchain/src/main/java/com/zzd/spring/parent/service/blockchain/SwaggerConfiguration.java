package com.zzd.spring.parent.service.blockchain;

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
                .apis(RequestHandlerSelectors.basePackage("com.zzd.spring.parent.service.blockchain.controller"))
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
                .title("区块链平台")//大标题
                .termsOfServiceUrl("http://localhost:8080")
                .version("2.0")//版本
                .build();
    }
    
}