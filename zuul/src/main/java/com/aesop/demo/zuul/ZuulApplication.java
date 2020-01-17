package com.aesop.demo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        System.out.println("http://localhost:8080/feign/sayHi?name=rfc-feign&token=aesop");
        System.out.println("http://localhost:8080/feign/showJCoDestinationState?token=aesop");
        System.out.println("http://localhost:8080/feign/switchJCoServerState?switchCode=1&token=aesop");
        System.out.println("http://localhost:8080/feign/switchJCoIDocServerState?switchCode=1&token=aesop");
    }

}
