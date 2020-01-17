package com.aesop.demo.rfcclient.app.controller.idoc;


import com.aesop.demo.rfcclient.app.bean.idoc.entity.po.PoHeader;
import com.aesop.demo.rfcclient.app.service.idoc.PoHeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Aesop
 * @since 2020-01-17
 */
@Slf4j
@Api(tags = "PO头数据")
@RestController
@RequestMapping("/po_header/po-header")
public class PoHeaderController {

    @Autowired
    private PoHeaderService poHeaderService;

    @ApiOperation(value = "显示PO头数据")
    @RequestMapping("/showPoHeader")
    @PostMapping
    public List<PoHeader> showPoHeader() {
        return poHeaderService.list();
    }


}
