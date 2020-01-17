package com.aesop.demo.rfcclient.infra.jcoserver.handler;

import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerTIDHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

/*
 * 该类用于在ABAP进行事务调用（CALL FUNCTION func IN BACKGROUND TASK DESTINATION dest）
 * 时， Java端需要实时告诉ABAP端目前事务处理的情况（状态），即Java与ABAP之间的事务状态的交流
 *
 */
@Slf4j
@Component
public class TIDHandler implements JCoServerTIDHandler {

    /**
     * 存储事务状态信息
     * This example uses a Hashtable to store status information.
     * Normally, however, you would use a database. If the DB is down throw a RuntimeException at this point.
     * JCo will then abort the tRFC and the R/3 backend will try again later.
     */

    Map<String, TIDState> availableTIDs = new Hashtable<String, TIDState>();

    /**
     * 当一个事务性RFM从ABAP端进行调用时，会触发此方法
     *
     * @param serverCtx serverCtx
     * @param tid       tid
     * @return "true" means that JCo will now execute the transaction, "false"  means that we have already executed this transaction previously, so JCo will skip the handleRequest() step and will immediately return an OK code to R/3.
     */
    public boolean checkTID(JCoServerContext serverCtx, String tid) {

//        log.info("TID Handler: checkTID for " + tid);

        TIDState state = availableTIDs.get(tid);

        if (state == null) {

            availableTIDs.put(tid, TIDState.CREATED);

            return true;

        }

        return state == TIDState.CREATED || state == TIDState.ROLLED_BACK;
    }

    /**
     * 事件提交时触发
     * e.g. commit on the database; if necessary throw a RuntimeException, if the commit was not possible
     *
     * @param serverCtx serverCtx
     * @param tid       tid
     */
    public void commit(JCoServerContext serverCtx, String tid) {

//        log.info("TID Handler: commit for " + tid);

        availableTIDs.put(tid, TIDState.COMMITTED);

    }

    /**
     * 事务回滚时触发
     * e.g. rollback on the database
     *
     * @param serverCtx serverCtx
     * @param tid       tid
     */
    public void rollback(JCoServerContext serverCtx, String tid) {

//        log.info("TID Handler: rollback for " + tid);

        availableTIDs.put(tid, TIDState.ROLLED_BACK);

    }

    public void confirmTID(JCoServerContext serverCtx, String tid) {

//        log.info("TID Handler: confirmTID for " + tid);

        try {

            // clean up the resources

        }

        // catch(Throwable t) {} //partner（代码ABAP对方） won't react on an

        // exception at

        // this point

        finally {

            availableTIDs.remove(tid);

        }

    }

    private enum TIDState {

        CREATED, COMMITTED, ROLLED_BACK, CONFIRMED

    }
}
