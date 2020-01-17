package com.aesop.demo.rfcclient.infra.jcoserver.handler;

import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.infra.jcoserver.repositories.ZSTFC_CONNECTIONRepository;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理来自ABAP端的调用请求，实现注册过的虚拟函数真正功能
 */
@Slf4j
public class ZSTFC_CONNECTIONHandler implements JCoServerFunctionHandler {

    /**
     * 处理远程调用请求
     *
     * @param serverCtx serverCtx
     * @param function  function
     */
    public void handleRequest(JCoServerContext serverCtx, JCoFunction function) {

        if (serverCtx.getRepository().getName().equals(JCoConstant.JCoCustomRepository.REPOSITORY_NAME) &&
                function.getName().equals(ZSTFC_CONNECTIONRepository.FUNCTION_NAME)) {

            log.info("\r\n----------------ZSTFC_CONNECTIONHandler----------------------");

            log.info("req text: " + function.getImportParameterList().getString("REQUTEXT"));

            function.getExportParameterList().setValue("ECHOTEXT", function.getImportParameterList().getString("REQUTEXT"));
            function.getExportParameterList().setValue("RESPTEXT", "\r\n这条消息是Java服务端 ZSTFC_CONNECTIONHandler 响应的");

            log.info("\r\ncall              : " + function.getName());// ABAP调用的是哪个函数
            log.info("\r\nConnectionId      : " + serverCtx.getConnectionID());
            log.info("\r\nSessionId         : " + serverCtx.getSessionID());
            log.info("\r\nTID               : " + serverCtx.getTID());
            log.info("\r\nrepository name   : " + serverCtx.getRepository().getName());
            log.info("\r\nis in transaction : " + serverCtx.isInTransaction());
            log.info("\r\nis stateful       : " + serverCtx.isStatefulSession());
            log.info("\r\n----------------------------------------------------------------");
            log.info("\r\ngwhost: " + serverCtx.getServer().getGatewayHost());
            log.info("\r\ngwserv: " + serverCtx.getServer().getGatewayService());
            log.info("\r\nprogid: " + serverCtx.getServer().getProgramID());
//            log.info("----------------------------------------------------------------");
//            log.info("attributes  : ");
//            log.info(serverCtx.getConnectionAttributes().toString());
            log.info("\r\n----------------------------------------------------------------");
            log.info("\r\nCPIC conversation ID: " + serverCtx.getConnectionAttributes().getCPICConversationID());
            log.info("\r\n----------------------------------------------------------------");

        }
    }

}
