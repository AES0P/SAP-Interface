package com.aesop.demo.rfcclient.infra.idoc.util;

import com.aesop.demo.rfcclient.app.bean.idoc.dto.po.Po;
import com.aesop.demo.rfcclient.app.bean.idoc.dto.po.PoIDocHeader;
import com.aesop.demo.rfcclient.app.bean.idoc.dto.po.PoIDocLines;
import com.aesop.demo.rfcclient.app.bean.idoc.entity.po.PoHeader;
import com.aesop.demo.rfcclient.app.bean.idoc.entity.po.PoLines;
import com.aesop.demo.rfcclient.app.service.idoc.PoHeaderService;
import com.aesop.demo.rfcclient.app.service.idoc.PoLinesService;
import com.aesop.demo.rfcclient.util.JaxbUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class IDocFileHandler {

    @Autowired
    private JaxbUtil jaxbUtil;

    @Autowired
    private PoHeaderService poHeaderService;

    @Autowired
    private PoLinesService poLinesService;

    public Boolean autoConvertObjectAndRecord(String filePath, String docType) {

        switch (docType) {

            case "ZIDOC_PO":

                List<PoHeader> headers4Save = new ArrayList<>();
                List<PoLines> lines4Save = new ArrayList<>();

                Po po = jaxbUtil.xmlFile2Object(filePath, Po.class);

                try {
                    for (PoIDocHeader header : po.getIDoc().getPo()) {

                        PoHeader poHeader = new PoHeader(
                                0,
                                Long.parseLong(header.getEbeln()),
                                header.getBukrs(),
                                header.getBstyp(),
                                header.getLoekz(),
                                header.getAedat(),
                                header.getErnam()
                        );

                        headers4Save.add(poHeader);

                        for (PoIDocLines lines : header.getPoIDocLinesList()) {

                            PoLines poLines = new PoLines(
                                    0,
                                    Long.parseLong(lines.getEbeln()),
                                    Integer.parseInt(lines.getEbelp()),
                                    lines.getLoekz(),
                                    lines.getAedat(),
                                    lines.getTxz01(),
                                    lines.getBukrs(),
                                    lines.getWerks(),
                                    lines.getLgort(),
                                    Float.parseFloat(lines.getMenge().trim()),
                                    lines.getMeins(),
                                    lines.getBprme(),
                                    Integer.parseInt(lines.getBpumz().trim())
                            );

                            lines4Save.add(poLines);

                        }
                    }

                    if (!headers4Save.isEmpty() && !lines4Save.isEmpty()) {

                        poHeaderService.saveBatch(headers4Save);
                        poLinesService.saveBatch(lines4Save);

                    }

                    log.info("convert !");

                } catch (Exception e) {
                    log.error(e.getMessage());
                    return false;
                }

            case "":

            default:
        }

        return true;

    }

}
