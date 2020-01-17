package com.aesop.demo.rfcclient.app.service.rfc;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.vo.log.TwerksVo;

import java.util.List;

/**
 * 结合SAP日志系统的演示程序
 *
 * @author tttttwtt
 */
public interface LogSystemDemoService {

    /**
     * add a record to sap log system
     */
    List<FeedBackDto> execute(List<TwerksVo> factoryList);
}
