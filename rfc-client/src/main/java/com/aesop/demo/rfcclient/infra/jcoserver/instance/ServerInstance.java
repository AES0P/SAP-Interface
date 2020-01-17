package com.aesop.demo.rfcclient.infra.jcoserver.instance;


import com.aesop.demo.rfcclient.infra.config.properties.JCoIDocServerProperties;
import com.aesop.demo.rfcclient.infra.config.properties.JCoServerProperties;
import com.aesop.demo.rfcclient.infra.jcoserver.handler.TIDHandler;
import com.aesop.demo.rfcclient.infra.jcoserver.listener.MyStateChangedListener;
import com.aesop.demo.rfcclient.infra.jcoserver.listener.MyThrowableListener;
import com.sap.conn.jco.JCoDestination;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 该类提供jcoDestination公有属性和方法
 */
@Slf4j
@Data
public abstract class ServerInstance {

    /**
     * jco server 配置文件后缀 .jcoServer
     */
    public static final String SERVER_SUFFIX = "jcoServer";

    /**
     * jco server 需要 JCo Destination 驱动
     * 可在已实现的四种 Destination 生产方式中任选
     * 需要注意的是，同时只能存在一个 jco destination的实例，所以只需生产一个 destination bean 即可
     * 这里使用 内存连接池 的 JCo destination
     */
    @Autowired
    public JCoDestination jCoDestination;

    /**
     * 在ABAP进行事务调用（CALL FUNCTION func IN BACKGROUND TASK DESTINATION dest）时，
     * Java端需要实时告诉ABAP端目前事务处理的情况（状态），即Java与ABAP之间的事务状态的交流
     */
    @Autowired
    public TIDHandler TIDHandler;

    /**
     * 异常监听，在连接过程中出问题时会被监听到，便于在连接过程中定位问题（可以不用设置）
     */
    @Autowired
    public MyThrowableListener throwableListener;

    /**
     * 连接状态监听
     */
    @Autowired
    public MyStateChangedListener stateChangedListener;

    @Autowired
    public JCoServerProperties jcoServerProperties;

    @Autowired
    public JCoIDocServerProperties jCoIDocServerProperties;

    public abstract Object getServerInstance();

    public abstract Boolean isValid();

    public abstract Properties getConnProperties();

    /**
     * 根据字符串名找到对应的类并返回一个实例
     *
     * @param className 类名
     * @return
     */
    public Object getClassInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 基于设定的connect properties生成连接配置文件，文件名后缀为 .jcoServer
     */
    public Boolean createServerPropertiesFile(String name, Properties properties) {

        File cfg = new File(name + "." + ServerInstance.SERVER_SUFFIX);

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
                createServerPropertiesFile(name, properties);
            }

            //删除失败，则采用原来的
            return true;

        }
    }

}

