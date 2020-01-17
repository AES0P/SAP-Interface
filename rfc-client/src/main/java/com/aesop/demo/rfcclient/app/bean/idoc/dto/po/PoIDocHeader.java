package com.aesop.demo.rfcclient.app.bean.idoc.dto.po;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@Data
@XmlRootElement(namespace = "", name = "ZIDOC_EKKO")
@XmlAccessorType(XmlAccessType.FIELD)
public class PoIDocHeader {

    /**
     * 采购凭证
     */
    @XmlElement(name = "EBELN", required = true)
    private String ebeln;

    /**
     * 公司代码
     */
    @XmlElement(name = "BUKRS", required = true)
    private String bukrs;

    /**
     * 凭证类别
     */
    @XmlElement(name = "BSTYP", required = true)
    private String bstyp;

    /**
     * 删除标识
     */
    @XmlElement(name = "LOEKZ", required = true)
    private String loekz;

    /**
     * 创建日期
     */
    @XmlElement(name = "AEDAT", required = true)
    private Date aedat;

    /**
     * 创建者
     */
    @XmlElement(name = "ERNAM", required = true)
    private String ernam;

    @XmlElement(name = "ZIDOC_EKPO", required = true)
    private List<PoIDocLines> poIDocLinesList;

}
