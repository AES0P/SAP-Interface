package com.aesop.demo.rfcclient.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 配置实现类
 * <p>
 * 1、导入Swaggerr依赖
 * 2、配置Docket的bean
 * 3、使用@Api等注解修饰
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .apiInfo(apiInfo())
                .select()// 选择那些路径和api会生成document,方法需要有ApiOperation注解才能生成接口文档
                //swagger要扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.aesop.demo.rfcclient.app.controller"))
//                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(PathSelectors.any())// 对所有路径进行监控
                .build();
//                .securitySchemes(security());// 如何保护我们的Api，有三种验证（ApiKey, BasicAuth, OAuth）
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger")//页面标题
                .description("Springboot接口文档")//描述
                .termsOfServiceUrl("localhost/dev")
                .contact(new Contact("Swagger测试", "localhost：4001/swagger-ui.html", "github.com/AES0P"))//创建人
                .version("1.0.0")//版本号
                .build();
    }


    private List<ApiKey> security() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("token", "token", "header"));
        return apiKeys;
    }

}
