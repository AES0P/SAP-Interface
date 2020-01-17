package com.aesop.demo.rfcclient.app.controller.idoc;


import com.aesop.demo.rfcclient.app.bean.idoc.entity.po.PoLines;
import com.aesop.demo.rfcclient.app.service.idoc.PoLinesService;
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
@Api(tags = "PO行数据")
@RestController
@RequestMapping("/po_lines/po-lines")
public class PoLinesController {

    @Autowired
    private PoLinesService poLinesService;

    @ApiOperation(value = "显示PO行数据")
    @RequestMapping("/showPoLines")
    @PostMapping
    public List<PoLines> showPoLines() {
        return poLinesService.list();
    }


}
