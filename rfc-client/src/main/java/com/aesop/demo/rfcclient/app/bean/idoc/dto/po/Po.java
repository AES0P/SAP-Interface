package com.aesop.demo.rfcclient.app.bean.idoc.dto.po;


import com.aesop.demo.rfcclient.infra.idoc.instance.IDocFile;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "ZIDOC_PO")
@XmlAccessorType(XmlAccessType.FIELD)
public class Po extends IDocFile {

    @XmlElement(name = "IDOC")
    private PoIDoc iDoc;

}
