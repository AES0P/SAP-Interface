package com.aesop.demo.rfcclient.app.bean.idoc.dto.po;

import com.aesop.demo.rfcclient.app.bean.idoc.IDocHeader;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * idoc file 会随 IDOC类型改变
 */
@Data
@XmlRootElement(name = "IDOC")
@XmlAccessorType(XmlAccessType.FIELD)
public class PoIDoc {

    @XmlElement(name = "EDI_DC40")
    private IDocHeader header;

    @XmlElement(name = "ZIDOC_EKKO")
    private List<PoIDocHeader> po;

}
