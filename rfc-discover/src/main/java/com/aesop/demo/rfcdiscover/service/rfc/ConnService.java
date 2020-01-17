package com.aesop.demo.rfcdiscover.service.rfc;

import com.aesop.demo.rfcdiscover.service.rfc.impl.ConnServiceFailureImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "EUREKA-RFC-CLIENT", fallback = ConnServiceFailureImpl.class) // value调用的服务的名称,fallback熔断器
public interface ConnService {

    //这里使用EUREKA-RFC-CLIENT中定义的方法的路径名进行匹配
    @RequestMapping("/hi")
    String sayHi(@RequestParam String name);

    @RequestMapping("/showJCoDestinationState")
    String showJCoDestinationState();

    @RequestMapping("/switchJCoServerState")
    String switchJCoServerState(@RequestParam int switchCode);

    @RequestMapping("/switchJCoIDocServerState")
    String switchJCoIDocServerState(@RequestParam int switchCode);

}
