package com.aesop.demo.rfcclient.infra.jcoserver.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import com.sap.conn.jco.server.JCoServerExceptionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * JCO server 异常处理
 */
@Slf4j
@Component
public class MyThrowableListener implements JCoServerErrorListener, JCoServerExceptionListener {

    public void serverErrorOccurred(JCoServer jcoServer, String connectionId, JCoServerContextInfo serverCtx, Error error) {

        log.info("\r\n>>> Error occured on " + jcoServer.getProgramID() + " connection " + connectionId);

        error.printStackTrace();

    }

    //服务异常监听器
    public void serverExceptionOccurred(JCoServer jcoServer, String connectionId, JCoServerContextInfo serverCtx, Exception error) {

        log.info("\r\n>>> Error occured on " + jcoServer.getProgramID() + " connection " + connectionId);

        error.printStackTrace();

    }

}
