package com.aesop.demo.rfcclient.app.controller.idoc;

import com.aesop.demo.rfcclient.app.service.idoc.SendIDocXmlFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(tags = "发送IDOC至SAP")
@RestController("SendIDocController")
@RequestMapping("idoc/send")
public class SendIDocController {

    @Autowired
    SendIDocXmlFileService sendIDocXmlFileService;

    @ApiOperation(value = "解析并发送本地 idoc xml 文件(绝对路径)")
    @RequestMapping("/sendLocalIDocFile")
    @PostMapping
    public String sendLocalIDocFile(@RequestBody String path) {

        try {
            sendIDocXmlFileService.sendLocalFileToSap(path);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @ApiOperation(value = "解析并发送网络 idoc xml 文件（URL）")
    @RequestMapping("/sendOnlineFileToSap")
    @PostMapping
    public String sendOnlineFileToSap(@RequestBody String url) {

        try {
            sendIDocXmlFileService.sendOnlineFileToSap(url, 1);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }

    }


}
