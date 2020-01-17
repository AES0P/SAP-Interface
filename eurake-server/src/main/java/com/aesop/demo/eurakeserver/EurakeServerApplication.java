package com.aesop.demo.eurakeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakeServerApplication.class, args);
        System.out.println("Eureka已实现集群设置，访问地址如下：");
        System.out.println("http://eureka-server-1:8761/");
        System.out.println("http://eureka-server-2:8762/");
    }

}
