package com.aesop.demo.rfcclient.infra.jco.instance;


import com.aesop.demo.rfcclient.infra.config.properties.JCoDestinationProperties;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 该类提供jcoDestination公有属性和方法
 */
@Slf4j
@Component
@Data
public abstract class JCoInstance {

    public JCoDestination jcoDestination;

    @Autowired
    private JCoDestinationProperties jcoDestinationProperties;

    /**
     * 子类重写
     *
     * @return
     * @throws JCoException
     */
    public abstract JCoDestination getDirectConn() throws JCoException;

    /**
     * 子类重写
     *
     * @return
     * @throws JCoException
     */
    public abstract JCoDestination getPooledConn() throws JCoException;

    public Properties getJCoConnProperties() {

        Properties connectProperties = new Properties();

        // SAP服务器地址
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, jcoDestinationProperties.getHost());

        // 实例编号
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, jcoDestinationProperties.getSysnr());

        // 集团号
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, jcoDestinationProperties.getClient());

        // 接口账号
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, jcoDestinationProperties.getUserid());

        // 密码，区分大小写
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, jcoDestinationProperties.getPassword());

        // 语言环境
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, jcoDestinationProperties.getLanguage());

        //最大活动连接数
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, jcoDestinationProperties.getPeakLimit().toString());

        // 空闲连接数，如果为0，则没有连接池效果，默认为1
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, jcoDestinationProperties.getPoolCapacity().toString());

        return connectProperties;

    }

    public void showJcoInfo() throws JCoException {

        if (isValid()) {
            log.info("\r\n" + "Destination - " + this.jcoDestination.getDestinationName() + " is ok");
            log.info("\r\n" + this.jcoDestination.getAttributes().toString());

        } else {
            log.info("\r\n" + "Failed to create JCO destination .");
        }

    }

    public Boolean isValid() {

        if (this.jcoDestination == null) {
            return false;
        }

        try {
            this.jcoDestination.ping();
            return true;
        } catch (JCoException ex) {
            log.warn("\r\n" + ex.getMessage());
            return false;
        }

    }

}

