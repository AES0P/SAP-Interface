package com.aesop.demo.rfcclient.app.service.rfc.impl;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.dto.log.CclogDto;
import com.aesop.demo.rfcclient.app.bean.rfc.dto.log.EilogDto;
import com.aesop.demo.rfcclient.app.bean.rfc.dto.log.IelogDto;
import com.aesop.demo.rfcclient.app.bean.rfc.dto.log.TwerksDto;
import com.aesop.demo.rfcclient.app.bean.rfc.vo.log.TwerksVo;
import com.aesop.demo.rfcclient.app.service.rfc.LogSystemDemoService;
import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.infra.rfc.instance.RFCInstance;
import com.sap.conn.jco.JCoFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * call rfc test
 * RFC调用模板示例
 *
 * @author tttttwtt
 */
@Slf4j
@Service
public class LogSystemDemoServiceImpl extends RFCInstance implements LogSystemDemoService {

    @Bean("SAP-RFC-" + JCoConstant.RFCList.ZZF_IF001)
    @Override
    public JCoFunction initFunction() {
        setFunctionName(JCoConstant.RFCList.ZZF_IF001);
        return initRFC();
    }

    @Override
    public List<FeedBackDto> execute(List<TwerksVo> factoryList) {

        //传入业务数据
        invokeLogData(factoryList);

        //获取SAP反馈信息
        List<FeedBackDto> feedBackDtoList = getResponseDTOS();

        log.info("\r\nC_CHAR: " + getChangingParameter("C_CHAR"));

        // 获取 I_LOG 的值，在DEMO RFC中它的值直接 copy E_LOG 的值
        EilogDto eilogDto = new EilogDto();
        getExportParameterStructure("I_LOG", eilogDto);
        log.info("\r\neilogDto-SENDR: " + eilogDto.getSendr());
        log.info("\r\neilogDto-RECER: " + eilogDto.getRecer());

        CclogDto clogDto = new CclogDto();
        getChangingParameterStructure("C_LOG", clogDto);
        log.info("\r\nclogDto-SENDR: " + clogDto.getSendr());
        log.info("\r\nclogDto-RECER: " + clogDto.getRecer());

        return feedBackDtoList;
    }

    public void invokeLogData(List<TwerksVo> factoryList) {

        // 将所有 vo 转成 dto
        List<TwerksDto> twerksDtoList = new ArrayList<>();
        factoryList.forEach(twerksVo -> {
            TwerksDto twerksDto = new TwerksDto(twerksVo.getFactoryCode());
            twerksDtoList.add(twerksDto);
        });

        //下面这俩演示用结构的就不通过外部系统传值了
        IelogDto ielogDto = new IelogDto(JCoConstant.ExternalSystemCode.SRM, JCoConstant.ExternalSystemCode.SAP);

        CclogDto cclogDto = new CclogDto(JCoConstant.ExternalSystemCode.SRM, JCoConstant.ExternalSystemCode.SAP);

        //VO转换为DTO后，传给SAP
        // 仅测试给导入结构赋值，SAP里对应RFC将不能对它做任何处理
        fillImportParameterStructure("E_LOG", ielogDto);

        // 仅测试给changing参数赋值，一般RFC无需设置 changingList，这里就不从外部系统传值了，直接设个默认值 X 测试
        fillChangingParameter("C_CHAR", "X");

        // 仅测试给changing结构赋值，一般RFC无需设置 changingList
        fillChangingParameterStructure("C_LOG", cclogDto);

        // 给Table赋值(表参数不区分传入传出，需要和SAP相关人员沟通好,例如传入表用I_开头，传出表用E_开头)
        fillInputParameterTable("T_WERKS", twerksDtoList);

    }


}
