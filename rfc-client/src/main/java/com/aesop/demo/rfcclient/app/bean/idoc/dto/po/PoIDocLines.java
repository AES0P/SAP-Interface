package com.aesop.demo.rfcclient.app.bean.idoc.dto.po;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement(namespace = "", name = "ZIDOC_EKPO")
@XmlAccessorType(XmlAccessType.FIELD)
public class PoIDocLines {

    /**
     * 采购凭证
     */
    @XmlElement(name = "EBELN", required = true)
    private String ebeln;

    /**
     * 项目
     */
    @XmlElement(name = "EBELP", required = true)
    private String ebelp;

    /**
     * 删除标识
     */
    @XmlElement(name = "LOEKZ", required = true)
    private String loekz;

    /**
     * 更改日期
     */
    @XmlElement(name = "AEDAT", required = true)
    private Date aedat;

    /**
     * 短文本
     */
    @XmlElement(name = "TXZ01", required = true)
    private String txz01;

    /**
     * 公司代码
     */
    @XmlElement(name = "AEDAT", required = true)
    private String bukrs;

    /**
     * 工厂
     */
    @XmlElement(name = "WERKS", required = true)
    private String werks;

    /**
     * 库存地点
     */
    @XmlElement(name = "AEDAT", required = true)
    private String lgort;

    /**
     * 采购订单数量
     */
    @XmlElement(name = "MENGE", required = true)
    private String menge;

    /**
     * 订单单位
     */
    @XmlElement(name = "MEINS", required = true)
    private String meins;

    /**
     * 订单价格单位
     */
    @XmlElement(name = "BPRME", required = true)
    private String bprme;

    /**
     * 数量转换
     */
    @XmlElement(name = "BPUMZ", required = true)
    private String bpumz;


}
