package com.aesop.demo.rfcclient.infra.idoc.handler.impl;

import com.aesop.demo.rfcclient.app.bean.idoc.entity.schedule.IDocFileConvertTask;
import com.aesop.demo.rfcclient.app.service.idoc.IDocScheduleService;
import com.aesop.demo.rfcclient.infra.config.properties.JCoIDocServerProperties;
import com.sap.conn.idoc.IDocDocumentList;
import com.sap.conn.idoc.IDocXMLProcessor;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.idoc.jco.JCoIDocHandler;
import com.sap.conn.jco.server.JCoServerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class IDocTIDHandler implements JCoIDocHandler {

    @Autowired
    private JCoIDocServerProperties properties;

    @Autowired
    private IDocScheduleService iDocScheduleService;

    /**
     * 该方法用来侦听SAP的IDOC 端口，如果有idoc发送到该端口，该方法就会把该idoc生成xml文件
     */
    public void handleRequest(JCoServerContext serverCtx, IDocDocumentList idocList) {

        //根据IDOC TYPE存放不同的目录方便管理
        String directory = properties.getDirectory() + idocList.getIDocType();

        //目录不存在则创建目录
        File path = new File(directory);
        if (!path.exists()) {
            path.mkdirs(); //创建目录
        }

        //自定义文件名规则：接收时间 + SAP idoc号
        String fileName = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(new Date(System.currentTimeMillis()))
                + "_"
                + idocList.get(0).getIDocNumber()
                + ".xml ";

        String filePath = directory + "/" + fileName;

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {

            IDocXMLProcessor xmlProcessor = JCoIDoc.getIDocFactory().getIDocXMLProcessor();
            fos = new FileOutputStream(filePath);
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            xmlProcessor.render(idocList, osw, IDocXMLProcessor.RENDER_WITH_TABS_AND_CRLF);

            osw.flush();

        } catch (Throwable thr) {
            thr.printStackTrace();
        } finally {
            try {

                if (osw != null)
                    osw.close();
                if (fos != null)
                    fos.close();

                //TODO 触发（定时/多线程）任务去自动将XML文件转换成java bean/json，并记录到接口表
                log.info("\r\n IDoc size: " + idocList.size() + ","
                        + "transfer idoc: " + idocList.get(0).getIDocNumber()
                        + " etc to xml file " + filePath);

                //把接收到的IDOC记录到日志表，然后定时任务去定时查表，将未成功转换为bean并记录到接口表的数据进行转换&记录
                iDocScheduleService.saveTask(new IDocFileConvertTask(
                                0,
                                idocList.getIDocType(),
                                directory,
                                fileName,
                                filePath,
                                0,
                                new java.sql.Date(System.currentTimeMillis()),
                                new Time(System.currentTimeMillis()),
                                0,
                                0
                        )
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
