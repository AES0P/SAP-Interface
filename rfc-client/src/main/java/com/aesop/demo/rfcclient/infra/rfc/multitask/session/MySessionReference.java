package com.aesop.demo.rfcclient.infra.rfc.multitask.session;

import com.sap.conn.jco.ext.JCoSessionReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 远程会话实现
 *
 * @author tttttwtt
 */
@Slf4j
@Component
public class MySessionReference implements JCoSessionReference {

    private AtomicInteger atomicInt = new AtomicInteger(0);

    // 远程会话ID
    private String id = "session-" + atomicInt.addAndGet(1);

    public void contextFinished() {
        log.info("context finished");
    }

    public void contextStarted() {
        log.info("context started");
    }

    public String getID() {

        return id;

    }

}
