package com.aesop.demo.rfcdiscover.controller.rfc;

import com.aesop.demo.rfcdiscover.service.rfc.ConnService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ConnController {

    @Resource
    private ConnService connService;

    @RequestMapping("/sayHi")
    public String sayHi(@RequestParam String name) {
        return connService.sayHi(name);
    }

    @RequestMapping("/showJCoDestinationState")
    public String showJCoDestinationState() {
        return connService.showJCoDestinationState();
    }

    @RequestMapping("/switchJCoServerState")
    public String switchJCoServerState(@RequestParam int switchCode) {
        return connService.switchJCoServerState(switchCode);
    }

    @RequestMapping("/switchJCoIDocServerState")
    public String switchJCoIDocServerState(@RequestParam int switchCode) {
        return connService.switchJCoIDocServerState(switchCode);
    }

}
