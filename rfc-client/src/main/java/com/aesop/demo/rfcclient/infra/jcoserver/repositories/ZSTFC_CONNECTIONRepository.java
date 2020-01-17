package com.aesop.demo.rfcclient.infra.jcoserver.repositories;

import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Component;

/**
 * 在Java端编写ABAP程序
 * <p>
 * 注：ZSTFC_CONNECTION函数不必要在ABAP端时行定义了（只定义签名，不需要实现），因为在这里（Java）
 * 进行了动态的函数对象创建的创建与注册
 */
@Component
public class ZSTFC_CONNECTIONRepository {


    public static final String FUNCTION_NAME = "ZSTFC_CONNECTION";

    /**
     * 对应RFC的 import 参数列表
     */
    JCoListMetaData impList;

    /**
     * 对应RFC的 export 参数列表
     */
    JCoListMetaData expList;

    /**
     * 对应RFC的 changing 参数列表
     */
    JCoListMetaData changingList;

    /**
     * 对应RFC的 table 参数列表
     */
    JCoListMetaData tableList;

    /**
     * 对应RFC的 exception 参数列表
     */
    JCoListMetaData exceptionList;


    public JCoCustomRepository getCustomRepository() {

        impList = JCo.createListMetaData("IMPORT");
        impList.add("REQUTEXT", JCoMetaData.TYPE_CHAR, 100, 50, 0, null, null, JCoListMetaData.IMPORT_PARAMETER, null, null);

        impList.lock();// 锁住，不允许再修改

        expList = JCo.createListMetaData("EXPORT");

        expList.add("RESPTEXT", JCoMetaData.TYPE_CHAR, 100, 50, 0, null, null, JCoListMetaData.EXPORT_PARAMETER, null, null);
        expList.add("ECHOTEXT", JCoMetaData.TYPE_CHAR, 100, 50, 0, null, null, JCoListMetaData.EXPORT_PARAMETER, null, null);

        expList.lock();


        JCoFunctionTemplate functionTemplate = JCo.createFunctionTemplate(FUNCTION_NAME, impList, expList, null, null, null);

        JCoCustomRepository customRepository = JCo.createCustomRepository(JCoConstant.JCoCustomRepository.REPOSITORY_NAME);

        customRepository.addFunctionTemplateToCache(functionTemplate);

        return customRepository;
    }


}
