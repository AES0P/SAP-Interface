package com.aesop.demo.rfcclient.app.controller.rfc;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.vo.pr.TprCancelVo;
import com.aesop.demo.rfcclient.app.service.rfc.PrCancelService;
import com.aesop.demo.rfcclient.infra.config.constant.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = JCoConstant.RFCList.ZZF_IF002)
@RestController("PrCancelController")
@RequestMapping("/pr/pr-cancel")
public class PrCancelController {

    @Autowired
    private PrCancelService prCancelService;

    @ApiOperation(value = "删除采购申请行")
    @RequestMapping("/cancel")
    @PostMapping
    public List<FeedBackDto> cancel(@RequestBody List<TprCancelVo> tprCancelVoList) {
        return prCancelService.prCancel(tprCancelVoList);
    }

}
