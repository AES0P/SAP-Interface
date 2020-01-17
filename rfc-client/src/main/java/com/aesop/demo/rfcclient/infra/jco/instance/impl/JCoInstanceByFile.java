package com.aesop.demo.rfcclient.infra.jco.instance.impl;

import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.infra.jco.instance.JCoInstance;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 通过配置文件创建ABAP连接
 */
@Slf4j
@Component
public class JCoInstanceByFile extends JCoInstance {

    /**
     * jco 配置文件后缀 .jcoDestination
     */
    public static final String SUFFIX = "jcoDestination";

    @Override
    //    @Bean(name = "getDirectConnByFile")zz
    public JCoDestination getDirectConn() throws JCoException {
        // 生成配置文件 ABAP_AS_WITHOUT_POOL.jcoDestination
        if (createJCoPropertiesFile(JCoConstant.JCoConn.ABAP_AS_WITHOUT_POOL, getJCoConnProperties())) {
            if (!isValid()) {
                this.jcoDestination = JCoDestinationManager.getDestination(JCoConstant.JCoConn.ABAP_AS_WITHOUT_POOL);
                showJcoInfo();
            } else {
                log.warn("No need to initialize JCo Destination repeatedly");
            }
        }

        return this.jcoDestination;
    }

    @Override
    @Bean(name = "getPooledConnByFile")
    public JCoDestination getPooledConn() throws JCoException {
        // 生成配置文件 ABAP_AS_WITH_POOL.jcoDestination
        if (createJCoPropertiesFile(JCoConstant.JCoConn.ABAP_AS_WITH_POOL, getJCoConnProperties())) {
            if (!isValid()) {
                this.jcoDestination = JCoDestinationManager.getDestination(JCoConstant.JCoConn.ABAP_AS_WITH_POOL);
                showJcoInfo();
            } else {
                log.warn("No need to initialize JCo Destination repeatedly");
            }
        }
        return this.jcoDestination;
    }

    /**
     * 基于设定的connect properties生成连接配置文件，文件名后缀为 .jcoDestination
     */
    private Boolean createJCoPropertiesFile(String name, Properties properties) {

        File cfg = new File(name + "." + JCoInstanceByFile.SUFFIX);

        //不存在，则创建
        if (!cfg.exists()) {

            try {
                FileOutputStream fos = new FileOutputStream(cfg, false);
                properties.store(fos, "Create by Aesop");
                fos.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {

            //存在，则删除原来的重新创建
            if (cfg.delete()) {
                createJCoPropertiesFile(name, properties);
            }

            //删除失败，则采用原来的
            return true;

        }
    }


}
