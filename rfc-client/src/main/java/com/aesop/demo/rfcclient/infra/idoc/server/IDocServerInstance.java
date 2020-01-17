package com.aesop.demo.rfcclient.infra.idoc.server;

import com.aesop.demo.rfcclient.infra.idoc.handler.impl.IDocHandlerFactory;
import com.aesop.demo.rfcclient.infra.jcoserver.handler.TIDHandler;
import com.aesop.demo.rfcclient.infra.jcoserver.instance.ServerInstance;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.idoc.jco.JCoIDocServer;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.ServerDataProvider;
import com.sap.conn.jco.server.JCoServerState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class IDocServerInstance extends ServerInstance {

    public static final String SERVER_NAME = "JCO_IDOC_SERVER";

    private JCoIDocServer jCoIDocServer;

    @Autowired
    private IDocHandlerFactory iDocHandlerFactory;

    @Autowired
    public TIDHandler TIDHandler;

    /**
     * JCoDestination建立成功后，jcoServer 才能创建
     */
    @Override
    @Bean(name = "getJCoIDocServerInstance")
    public JCoIDocServer getServerInstance() {

        if (!jCoDestination.getDestinationName().isEmpty()) {

            if (createServerPropertiesFile(SERVER_NAME, getConnProperties())) {
                //防止重复初始化
                if (!isValid()) {

                    //通过配置文件尝试建立 server
                    try {
                        this.jCoIDocServer = JCoIDoc.getServer(SERVER_NAME);
                    } catch (JCoException ex) {
                        throw new RuntimeException("Unable to create the server " + SERVER_NAME + " because of " + ex.getMessage(), ex);
                    }

                    this.jCoIDocServer.setIDocHandlerFactory(iDocHandlerFactory);

                    this.jCoIDocServer.setTIDHandler(TIDHandler);

                    //错误/异常监听处理器
                    this.jCoIDocServer.addServerErrorListener(throwableListener);
                    this.jCoIDocServer.addServerExceptionListener(throwableListener);

                    //状态监听器
                    this.jCoIDocServer.addServerStateChangedListener(stateChangedListener);

                    //启动jcoServer
                    this.jCoIDocServer.start();

                    log.info("JCo IDoc Server initialized successfully...");

                } else {
                    log.warn("No need to initialize JCO IDoc Server repeatedly");
                }
            }
        }
        return this.jCoIDocServer;
    }

    @Override
    public Boolean isValid() {
        return jCoIDocServer != null &&
                (this.jCoIDocServer.getState().equals(JCoServerState.STARTED) || this.jCoIDocServer.getState().equals(JCoServerState.ALIVE));
    }

    @Override
    public Properties getConnProperties() {

        Properties connectProperties = new Properties();

        //IP
        connectProperties.setProperty(ServerDataProvider.JCO_GWHOST, jCoIDocServerProperties.getHost());

        //JCO_GWSERV
        connectProperties.setProperty(ServerDataProvider.JCO_GWSERV, jCoIDocServerProperties.getServer());

        //JCO_PROGID
        connectProperties.setProperty(ServerDataProvider.JCO_PROGID, jCoIDocServerProperties.getProgid());

        //JCO_REP_DEST
        connectProperties.setProperty(ServerDataProvider.JCO_REP_DEST, jCoDestination.getDestinationName());

        //JCO_CONNECTION_COUNTS
        connectProperties.setProperty(ServerDataProvider.JCO_CONNECTION_COUNT, jCoIDocServerProperties.getConn_count());

        return connectProperties;

    }

}
