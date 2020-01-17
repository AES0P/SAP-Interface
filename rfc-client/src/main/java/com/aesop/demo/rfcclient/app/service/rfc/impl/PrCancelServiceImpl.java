package com.aesop.demo.rfcclient.app.service.rfc.impl;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.dto.pr.TprCancelDto;
import com.aesop.demo.rfcclient.app.bean.rfc.vo.pr.TprCancelVo;
import com.aesop.demo.rfcclient.app.service.rfc.PrCancelService;
import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.infra.rfc.instance.RFCInstance;
import com.sap.conn.jco.JCoFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除SAP中指定PR的PR行
 *
 * @author tttttwtt
 */
@Service
public class PrCancelServiceImpl extends RFCInstance implements PrCancelService {

    @Bean("SAP-RFC-" + JCoConstant.RFCList.ZZF_IF002)
    @Override
    public JCoFunction initFunction() {
        setFunctionName(JCoConstant.RFCList.ZZF_IF002);
        return initRFC();
    }

    @Override
    public List<FeedBackDto> prCancel(List<TprCancelVo> tprCancelVoList) {

        //传入业务数据
        invokePrCancel(tprCancelVoList);

        //获取SAP反馈信息
        return getResponseDTOS();
    }


    public void invokePrCancel(List<TprCancelVo> tprCancelVoList) {

        // vo to dto
        List<TprCancelDto> tPrCancelDtoList = new ArrayList<>();
        tprCancelVoList.forEach(tprCancelVo -> {
            TprCancelDto tprCancelDto = new TprCancelDto(tprCancelVo.getPrNo(), tprCancelVo.getPrLine());
            tPrCancelDtoList.add(tprCancelDto);
        });

        // 通过映射自动填充字段
        fillInputParameterTable("I_ITEMS", tPrCancelDtoList);

    }


}
