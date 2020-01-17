package com.aesop.demo.rfcdiscover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix//开启断路器
@EnableHystrixDashboard//开启断路器指标看板
@EnableSwagger2
public class RfcDiscoverApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfcDiscoverApplication.class, args);
        System.out.println("断路器指示板： http://localhost:5001/hystrix");
        System.out.println("Swagger   ： http://localhost:5001/doc.html");
    }

}
