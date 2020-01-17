package com.aesop.demo.rfcclient.infra.rfc.multitask.session;

import com.aesop.demo.rfcclient.infra.rfc.multitask.threaded.WorkerThread;
import com.sap.conn.jco.ext.JCoSessionReference;
import com.sap.conn.jco.ext.SessionException;
import com.sap.conn.jco.ext.SessionReferenceProvider;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class MySessionReferenceProvider implements SessionReferenceProvider {

    @Resource
    WorkerThread workerThread;

    public JCoSessionReference getCurrentSessionReference(String scopeType) {

        // 从当前线程中读取相应的远程会话，这样确保了同一任务中的多个调用是在同一远程会话连接中执行的
        MySessionReference sesRef = workerThread.localSessionReference.get();

        if (sesRef != null) {

            return sesRef;

        }

        throw new RuntimeException("Unknown thread:" + Thread.currentThread().getId());

    }

    // 远程会话是否活着，JCo框架调用此来决定此连接是否销毁？
    public boolean isSessionAlive(String sessionId) {

        Collection<MySessionReference> availableSessions = workerThread.sessions.values();

        for (MySessionReference ref : availableSessions) {

            if (ref.getID().equals(sessionId)) {

                return true;

            }

        }

        return false;

    }

    public void jcoServerSessionContinued(String sessionID)

            throws SessionException {

    }

    public void jcoServerSessionFinished(String sessionID) {

    }

    public void jcoServerSessionPassivated(String sessionID)

            throws SessionException {

    }

    public JCoSessionReference jcoServerSessionStarted()

            throws SessionException {

        return null;

    }

}
