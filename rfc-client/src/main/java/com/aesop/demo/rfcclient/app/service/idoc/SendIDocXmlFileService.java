package com.aesop.demo.rfcclient.app.service.idoc;

import com.sap.conn.idoc.IDocParseException;
import com.sap.conn.jco.JCoException;

import java.io.IOException;

/**
 * 解析并发送 idoc xml 文件到 SAP
 * 提供解析本地 文件 和 URL 两种方式
 */
public interface SendIDocXmlFileService {

    void sendLocalFileToSap(String filePath) throws IOException, JCoException, IDocParseException;

    void sendOnlineFileToSap(String url, int saveCode) throws IDocParseException, JCoException, IOException;

}
