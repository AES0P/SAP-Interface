package com.aesop.demo.rfcclient.app.bean.idoc;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * idoc 头部信息 不会随 IDOC类型改变
 */
@Data
@XmlRootElement(name = "EDI_DC40")
@XmlAccessorType(XmlAccessType.FIELD)
public class IDocHeader {

    @XmlElement(name = "MANDT")
    private String clinet;

    @XmlElement(name = "DOCNUM")
    private String docNum;

    @XmlElement(name = "IDOCTYP")
    private String idocType;

    @XmlElement(name = "CREDAT")
    private String creationDate;

    @XmlElement(name = "CRETIM")
    private String creationTime;

    @XmlElement(name = "SERIAL")
    private String serial;

}
