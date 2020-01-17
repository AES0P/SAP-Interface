package com.aesop.demo.rfcclient.app.bean.rfc.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackDto {

    /**
     * sap 反馈信息 关键字
     */
    private String dataKey;

    /**
     * sap 反馈信息 信息标识
     */
    private String ifflg;

    /**
     * sap 反馈信息 长文本
     */
    private String ifmsg;

}
