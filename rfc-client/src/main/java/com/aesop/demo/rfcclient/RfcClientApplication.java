package com.aesop.demo.rfcclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableCaching//开启缓存
@ImportResource(locations = "classpath:/redis/Config-Listener.xml")//导入redis 订阅服务xml配置文件
@EnableSwagger2
public class RfcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfcClientApplication.class, args);
        log.info("\r\n http://localhost:4001/doc.html");
        log.info("\r\n http://localhost:4002/doc.html");
    }

}
