package com.aesop.demo.rfcclient.infra.jco.instance.impl;

import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.infra.jco.instance.JCoInstance;
import com.aesop.demo.rfcclient.infra.jco.provider.MyDestinationDataProvider;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 通过内存的方式创建连接，防止敏感信息外泄
 */
@Slf4j
@Component
public class JCoInstanceByMemory extends JCoInstance {

    @Override
//    @Bean(name = "getDirectConnByMemory")
    public JCoDestination getDirectConn() throws JCoException {
        if (!isValid()) {
            this.jcoDestination = getJCoDestinationInstance(JCoConstant.JCoConn.ABAP_AS_WITHOUT_POOL, getJCoConnProperties());
            showJcoInfo();
        } else {
            log.warn("No need to initialize JCo Destination repeatedly");
        }
        return this.jcoDestination;
    }

    @Override
//    @Primary //容器里存在多个同类型bean时，设定主bean，优先注入
//    @Bean(name = "getPooledConnByMemory")
    public JCoDestination getPooledConn() throws JCoException {
        if (!isValid()) {
            this.jcoDestination = getJCoDestinationInstance(JCoConstant.JCoConn.ABAP_AS_WITH_POOL, getJCoConnProperties());
            showJcoInfo();
        } else {
            log.warn("No need to initialize JCo Destination repeatedly");
        }
        return this.jcoDestination;
    }

    public JCoDestination getJCoDestinationInstance(String destinationName, Properties properties) throws JCoException {

        JCoDestination jcoDestination;

        // 获取单例
        MyDestinationDataProvider myProvider = MyDestinationDataProvider.getInstance();

        // Register the MyDestinationDataProvider 环境注册 只需注册一次，重复注册会报错
        if (!Environment.isDestinationDataProviderRegistered()) {
            Environment.registerDestinationDataProvider(myProvider);
        }

        // Add a destination
        myProvider.addDestination(destinationName, properties);

        jcoDestination = JCoDestinationManager.getDestination(destinationName);


        return jcoDestination;

    }

}
