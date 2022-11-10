package com.qfedu.fmmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-08-20  10:42
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*swagger 会帮助我们生成接口文档
    * 1.配置文档信息
    * 2.配置生成规则*/

    /*Docket封装接口文档信息*/
    @Bean
    public Docket getDocket(){
//        Docket docket = new Docket(DocumentationType.SWAGGER_2); //指定文档风格
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《锋迷商城》后端接口说明")
                .description("该文档详细说明了锋迷商城项目后端接口规范")
                .version("v 2.0.1")
                .contact(new Contact("邱清源","www.yuange.com","yuange@o.com"));
        ApiInfo apiInfo = apiInfoBuilder.build();

        //如何获取一个接口对象
        //new 接口，需要在构造器后的{}实现接口中的所有抽象方法
        //new 子类-实现类
        //工厂模式
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qfedu.fmmall.controller"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("/user/"))
                .build();
//        docket.apiInfo(apiInfo);//指定生成的文档中的封面信息:文档标题、版本、作者
        return docket;
    }
}
