package com.aesop.demo.rfcclient.app.bean.rfc.dto.pr;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 仅有与SAP交互时要传输的字段，不包含任何无关字段
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TprCancelDto {

    /**
     * sap 申请号
     */
    private String prNo;

    /**
     * sap 申请行号
     */
    private String prItem;

}
