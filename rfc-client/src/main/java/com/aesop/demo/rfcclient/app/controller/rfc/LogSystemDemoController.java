package com.aesop.demo.rfcclient.app.controller.rfc;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.vo.log.TwerksVo;
import com.aesop.demo.rfcclient.app.service.rfc.LogSystemDemoService;
import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = JCoConstant.RFCList.ZZF_IF001)
@RestController("LogSystemDemoController")
@RequestMapping("/log/test")
public class LogSystemDemoController {

    @Autowired
    private LogSystemDemoService logSystemDemoService;

    @ApiOperation(value = "日志记录示例程序")
    @RequestMapping("/execute")
    @PostMapping
    public List<FeedBackDto> execute(@RequestBody List<TwerksVo> factoryList) {
        return logSystemDemoService.execute(factoryList);
    }

}
