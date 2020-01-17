package com.aesop.demo.rfcclient.infra.jcoserver.instance.impl;

import com.aesop.demo.rfcclient.infra.jcoserver.handler.STFC_CONNECTIONHandler;
import com.aesop.demo.rfcclient.infra.jcoserver.handler.ZSTFC_CONNECTIONHandler;
import com.aesop.demo.rfcclient.infra.jcoserver.instance.ServerInstance;
import com.aesop.demo.rfcclient.infra.jcoserver.repositories.ZSTFC_CONNECTIONRepository;
import com.sap.conn.jco.JCoCustomRepository;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.ServerDataProvider;
import com.sap.conn.jco.server.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class JCoServerInstance extends ServerInstance {

    public static final String SERVER_NAME = "JCO_SERVER";

    private JCoServer jcoServer;

    @Autowired
    private ZSTFC_CONNECTIONRepository zstfc_connectionRepository;
    /**
     * 要处理的函数，可通过配置文件读取
     * 需要与 handleList 里的处理器名字一一对应
     * 这里面的函数名，供SAP端调用，它们的具体逻辑可以在JAVA端实现，也可在SAP端实现
     */
    public String[] functionList = {
            "STFC_CONNECTION",//这个函数在SAP端实现
            ZSTFC_CONNECTIONRepository.FUNCTION_NAME//这个函数在Java端实现
    };

    /**
     * 函数对应处理器，可通过配置文件读取
     * 需要与 functionList 里的函数名字一一对应
     */
    public String[] handleList = {
            STFC_CONNECTIONHandler.class.getName(),
            ZSTFC_CONNECTIONHandler.class.getName()
    };

    /**
     * JCoDestination建立成功后，jcoServer 才能创建
     */
    @Override
    @Bean(name = "getJCoServerInstance")
    public JCoServer getServerInstance() {

        if (functionList.length == 0 | handleList.length == 0) {
            log.warn("\r\n请将函数列表与对应处理器一一对应地设置!");
            return null;
        }

        if (!jCoDestination.getDestinationName().isEmpty()) {

            if (createServerPropertiesFile(SERVER_NAME, getConnProperties())) {
                //防止重复初始化
                if (!isValid()) {

                    //通过配置文件尝试建立 server
                    try {
                        this.jcoServer = JCoServerFactory.getServer(SERVER_NAME);
                    } catch (JCoException ex) {
                        throw new RuntimeException("Unable to create the server " + SERVER_NAME + " because of " + ex.getMessage(), ex);
                    }

                    //ABAP端的调用请求处理器，不同的方法最好设置不同的处理器，需要一一对应地设置
                    int count = 0;
                    DefaultServerHandlerFactory.FunctionHandlerFactory factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();

                    for (String function : functionList) {
                        factory.registerHandler(function, (JCoServerFunctionHandler) getClassInstance(handleList[count++]));
                    }

                    this.jcoServer.setCallHandlerFactory(factory);

                    //添加事务处理器,不设置factory无法起作用
                    this.jcoServer.setTIDHandler(TIDHandler);

                    //错误/异常监听处理器
                    this.jcoServer.addServerErrorListener(throwableListener);
                    this.jcoServer.addServerExceptionListener(throwableListener);

                    //状态监听器
                    this.jcoServer.addServerStateChangedListener(stateChangedListener);

                    //设置添加本地Repository库
                    String repDest = this.jcoServer.getRepositoryDestination();

                    JCoCustomRepository customRepository = zstfc_connectionRepository.getCustomRepository();

                    if (repDest != null) {
                        try {
                            customRepository.setDestination(JCoDestinationManager.getDestination(repDest));
                        } catch (JCoException e) {
                            e.printStackTrace();
                            log.info(">>> repository contains static function definition only");
                        }
                    }

                    this.jcoServer.setRepository(customRepository);

                    //启动jcoServer
                    try {
                        this.jcoServer.start();
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }

                    log.info("JCo Server initialized successfully...");

                } else {
                    log.warn("No need to initialize JCO Server repeatedly");
                }
            }
        }
        return this.jcoServer;
    }

    @Override
    public Boolean isValid() {
        return this.jcoServer != null &&
                (this.jcoServer.getState().equals(JCoServerState.STARTED) || this.jcoServer.getState().equals(JCoServerState.ALIVE));
    }

    @Override
    public Properties getConnProperties() {

        Properties connectProperties = new Properties();

        //IP
        connectProperties.setProperty(ServerDataProvider.JCO_GWHOST, jcoServerProperties.getHost());

        //JCO_GWSERV
        connectProperties.setProperty(ServerDataProvider.JCO_GWSERV, jcoServerProperties.getServer());

        //JCO_PROGID
        connectProperties.setProperty(ServerDataProvider.JCO_PROGID, jcoServerProperties.getProgid());

        //JCO_REP_DEST
        connectProperties.setProperty(ServerDataProvider.JCO_REP_DEST, jCoDestination.getDestinationName());

        //JCO_CONNECTION_COUNTS
        connectProperties.setProperty(ServerDataProvider.JCO_CONNECTION_COUNT, jcoServerProperties.getConn_count());

        return connectProperties;

    }


}
