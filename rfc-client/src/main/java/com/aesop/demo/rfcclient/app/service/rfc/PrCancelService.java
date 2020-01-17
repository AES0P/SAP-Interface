package com.aesop.demo.rfcclient.app.service.rfc;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.vo.pr.TprCancelVo;

import java.util.List;

/**
 * 申请取消
 *
 * @author tttttwtt
 */
public interface PrCancelService {

    /**
     * pr取消入口
     */
    List<FeedBackDto> prCancel(List<TprCancelVo> tprCancelVoList);
}
