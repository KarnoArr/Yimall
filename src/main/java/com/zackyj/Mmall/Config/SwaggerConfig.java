package com.zackyj.Mmall.Config;

import com.zackyj.Mmall.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.Config ON 2021/7/27-周二.
 */
@WebAppConfiguration
@EnableSwagger2
@EnableWebMvc
@EnableKnife4j
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                       .apiInfo(apiInfo()).select()
                       //扫描指定包中的swagger注解
                       .apis(RequestHandlerSelectors.basePackage("com.zackyj.Mmall.controller"))
                       //扫描所有有注解的api
                       //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                       .paths(PathSelectors.any())
                       .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                       .title("SSM_MALL Api Documentation")
                       .description("SSM_MALL项目API接口文档")
                       .termsOfServiceUrl("https://www.uuuvw.xyz/")
                       .license("zackyjz@qq.com")
                       .version("1.0.0")
                       .build();
    }
}
