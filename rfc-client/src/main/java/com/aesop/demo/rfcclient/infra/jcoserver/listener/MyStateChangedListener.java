package com.aesop.demo.rfcclient.infra.jcoserver.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerState;
import com.sap.conn.jco.server.JCoServerStateChangedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 服务状态改变监听器
 */
@Slf4j
@Component
public class MyStateChangedListener implements JCoServerStateChangedListener {

    /**
     * Defined states are: STARTED启动, DEAD死, ALIVE活, STOPPED停止;
     * see JCoServerState class for details.
     *
     * @param server   JCoServer
     * @param oldState JCoServer old state
     * @param newState JCoServer new state
     */
    public void serverStateChangeOccurred(JCoServer server, JCoServerState oldState, JCoServerState newState) {

        log.info("\r\nServer state changed from " + oldState.toString() + " to " + newState.toString()
                + " on server with program id " + server.getProgramID());

    }

}
