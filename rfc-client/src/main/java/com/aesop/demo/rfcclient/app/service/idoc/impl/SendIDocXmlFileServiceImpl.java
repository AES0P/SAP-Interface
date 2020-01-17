package com.aesop.demo.rfcclient.app.service.idoc.impl;

import com.aesop.demo.rfcclient.app.service.idoc.SendIDocXmlFileService;
import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.util.FileUtil;
import com.sap.conn.idoc.*;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
public class SendIDocXmlFileServiceImpl implements SendIDocXmlFileService {

    @Autowired
    private FileUtil fileUtil;

    /**
     * Send Local File To Sap
     *
     * @param filePath file Path
     */
    @Override
    public void sendLocalFileToSap(String filePath) throws IOException, JCoException, IDocParseException {
        File file = new File(filePath);
        sendIDoc(file);
    }

    /**
     * Send Online File To Sap
     *
     * @param url      online file's url
     * @param saveCode save(1) temp file or not(0)
     */
    @Override
    public void sendOnlineFileToSap(String url, int saveCode) throws IDocParseException, JCoException, IOException {
        //临时文件存放目录
        String dir = "rfc-client/src/main/resources/SAP/idocfiles" + "/tempfile";
        File file = new File(fileUtil.downloadFile(url, dir, "_idoc.xml"));
        sendIDoc(file);
        switch (saveCode) {
            case 0:
                file.delete();
                break;
            case 1:
                System.out.println("temp file path:" + file.getAbsolutePath());
                break;
        }
    }

    private void sendIDoc(File file) throws IOException, JCoException, IDocParseException {

        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String iDocXML = sb.toString();

        br.close();
        fileReader.close();

        JCoDestination destination = JCoDestinationManager.getDestination(JCoConstant.JCoConn.ABAP_AS_WITH_POOL);
        IDocRepository iDocRepository = JCoIDoc.getIDocRepository(destination);

        String tid = destination.createTID();

        IDocFactory iDocFactory = JCoIDoc.getIDocFactory();

        // use existent xml file
        IDocXMLProcessor processor = iDocFactory.getIDocXMLProcessor();
        IDocDocumentList iDocList = processor.parse(iDocRepository, iDocXML);
        JCoIDoc.send(iDocList, IDocFactory.IDOC_VERSION_DEFAULT, destination, tid);
        destination.confirmTID(tid);

        log.info("Send IDOC File To Sap Success ");
    }

}
