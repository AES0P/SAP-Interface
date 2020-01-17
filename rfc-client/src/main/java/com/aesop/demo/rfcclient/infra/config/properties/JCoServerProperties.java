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
@ConfigurationProperties(prefix = JCoServerProperties.SERVER_PREFIX)
@PropertySource(value = "/config/sap-config.properties")
@Data
public class JCoServerProperties {

    /**
     * 读取配置文件设置时的前缀
     */
    public static final String SERVER_PREFIX = "sap.jco.server";


    /**
     * SAP 服务器地址
     */
    private String host;

    /**
     * SAP server
     */
    private String server;

    /**
     * SAP program id
     */
    private String progid;

    /**
     * SAP destination name
     */
    private String destination;

    /**
     * SAP connection count
     */
    private String conn_count;


}
