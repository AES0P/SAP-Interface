package com.aesop.demo.rfcclient.infra.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置bean类
 * 将spring bean与配置关联
 */
@Component
@ConfigurationProperties(prefix = JCoDestinationProperties.PREFIX)
@PropertySource(value = "/config/sap-config.properties")
@Data
public class JCoDestinationProperties {

    /**
     * 读取配置文件设置时的前缀
     */
    public static final String PREFIX = "sap.jco";


    /**
     * SAP 服务器地址
     */
    private String host;

    /**
     * SAP 系统实例编号
     */
    private String sysnr;

    /**
     * SAP 集团号
     */
    private String client;

    /**
     * SAP 接口账号
     */
    private String userid;

    /**
     * SAP 密码，区分大小写
     */
    private String password;

    /**
     * SAP 语言环境
     */
    private String language;

    // 连接池方式与直连方式不同的是设置了下面两个连接属性
    /**
     * SAP 同时可创建的最大活动连接数，0表示无限制，默认为JCO_POOL_CAPACITY的值
     */
    private Integer peakLimit;

    /**
     * SAP 空闲连接数，如果为0，则没有连接池效果，默认为1
     */
    private Integer poolCapacity;


}
