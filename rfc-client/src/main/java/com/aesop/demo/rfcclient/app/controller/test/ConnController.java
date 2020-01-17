package com.aesop.demo.rfcclient.app.controller.test;

import com.sap.conn.idoc.jco.JCoIDocServer;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.server.JCoServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(tags = "ConnController")
@Controller
public class ConnController {

    @Value("${server.port}")
    private String port;

    /**
     * 提供四种jco destination,这里默认使用内存连接池方式
     * <p>
     * 1.getDirectConnByFile    文件直连
     * 2.getPooledConnByFile    文件连接池
     * 3.getDirectConnByMemory  内存直连
     * 4.getPooledConnByMemory  内存连接池
     */
    @Autowired
    private JCoDestination jCoDestination;

    // JCoServer 和 JCoIDocServer 同时只能启用一个
    @Resource(name = "getJCoServerInstance")
    private JCoServer jCoServer;

    @Resource(name = "getJCoIDocServerInstance")
    private JCoIDocServer iDocServer;

    @ApiOperation(value = "仅供Client端测试用")
    @RequestMapping("/hi")
    @ResponseBody
    public String sayHi(@RequestParam String name) {
        return "hi " + name + ",i am from port:" + port;
    }

    @ApiOperation(value = "显示 JCo Destination 属性")
    @RequestMapping("/showJCoDestinationState")
    @ResponseBody
    public String showJCoDestinationState() throws JCoException {
        return jCoDestination.getAttributes().toString();
    }

    @ApiOperation(value = "test JCo Server")
    @RequestMapping("/switchJCoServerState")
    @ResponseBody
    public String switchJCoServerState(@RequestParam int switchCode) {

        try {
            switch (switchCode) {
                case 0:
                    jCoServer.stop();
                    break;
                case 1:
                    jCoServer.start();
                    break;
                default:
                    break;
            }

            Thread.sleep(2500);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Jco Server State:" + jCoServer.getState();

    }

    @ApiOperation(value = "test JCo iDoc Server")
    @RequestMapping("/switchJCoIDocServerState")
    @ResponseBody
    public String switchJCoIDocServerState(@RequestParam int switchCode) {

        try {
            switch (switchCode) {
                case 0:
                    iDocServer.stop();
                    break;
                case 1:
                    iDocServer.start();
                    break;
                default:
                    break;
            }

            Thread.sleep(2500);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Jco IDoc Server State:" + iDocServer.getState();

    }


}
