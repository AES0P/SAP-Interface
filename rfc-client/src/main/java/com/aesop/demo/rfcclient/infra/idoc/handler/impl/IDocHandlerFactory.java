package com.aesop.demo.rfcclient.infra.idoc.handler.impl;

import com.sap.conn.idoc.jco.JCoIDocHandler;
import com.sap.conn.idoc.jco.JCoIDocHandlerFactory;
import com.sap.conn.idoc.jco.JCoIDocServerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IDocHandlerFactory implements JCoIDocHandlerFactory {

    @Autowired
    private IDocTIDHandler iDocTIDHandler;

    public JCoIDocHandler getIDocHandler(JCoIDocServerContext serverCtx) {
        return iDocTIDHandler;
    }


}
