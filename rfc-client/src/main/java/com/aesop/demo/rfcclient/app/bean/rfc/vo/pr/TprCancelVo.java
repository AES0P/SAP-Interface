package com.aesop.demo.rfcclient.app.bean.rfc.vo.pr;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请取消类
 * 可以包含与SAP交互时，SAP没有的字段，供JAVA端做一些业务处理
 *
 * @author tttttwtt
 */
@Getter
@Setter
public class TprCancelVo {

    /**
     * sap 申请号
     */
    private String prNo;

    /**
     * srm 申请号
     */
    private String srmPrNo;

    /**
     * sap 申请行号
     */
    private String prLine;

    /**
     * srm 申请行号
     */
    private String srmPrLine;


}
