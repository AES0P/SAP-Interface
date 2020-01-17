package com.aesop.demo.rfcclient.infra.jco.provider;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.util.HashMap;
import java.util.Properties;

/**
 * JcoInstanceByFile中的直连、连接池，两种连接方法都需要先建立一个属性配置文件，然后JCo再从建立好文件里读取连接到SAP服务器所需要的连接属性，
 * 这个方法很难在实际的环境中应用，因为存储SAP连接属性配置信息到一个文件里，是比较不安全的。
 * <p>
 * 这里通过DestinationDataProvider，将一个连接变量信息存放在内存中
 *
 * @author tttttwtt
 */
public class MyDestinationDataProvider implements DestinationDataProvider {

    private DestinationDataEventListener eL;

    private HashMap<String, Properties> destinations;

    private static MyDestinationDataProvider provider = new MyDestinationDataProvider();

    // 单例模式
    private MyDestinationDataProvider() {

        if (provider == null) {
            destinations = new HashMap<String, Properties>();
        }
    }

    public static MyDestinationDataProvider getInstance() {
        return provider;
    }

    // 实现接口：获取连接配置属性
    public Properties getDestinationProperties(String destinationName) {

        if (destinations.containsKey(destinationName)) {

            return destinations.get(destinationName);

        } else {

            throw new RuntimeException("Destination " + destinationName + " is not available");

        }

    }

    public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
        this.eL = eventListener;
    }

    public boolean supportsEvents() {
        return true;
    }

    /**
     * Add new destination 添加连接配置属性
     *
     * @param properties holds all the required data for a destination
     **/
    public void addDestination(String destinationName, Properties properties) {

        synchronized (destinations) {

            destinations.put(destinationName, properties);

        }

    }

    // }

}
