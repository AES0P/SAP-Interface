package com.aesop.demo.zuul.config;

import com.aesop.demo.zuul.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置自定义过滤器
 */
@Configuration
public class ZuulConfig {

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

}
