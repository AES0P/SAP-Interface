package com.aesop.demo.rfcclient.app.controller.rfc;


import com.aesop.demo.rfcclient.app.bean.rfc.entity.RfcLogFeedback;
import com.aesop.demo.rfcclient.app.service.rfc.RfcLogFeedbackService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * RfcLogFeedbackController
 * </p>
 *
 * @author Aesop
 * @since 2020-01-07
 */

@Api(tags = "RFC log")
@Controller
@RequestMapping("/log/rfc-log-feedback")
public class RfcLogFeedbackController {

    @Autowired
    private RfcLogFeedbackService feedbackService;

    @ApiOperation(value = "根据 log id 移除指定日志")
    @RequestMapping("/removeByLogId")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public void removeByLogId(@RequestParam("id") long id) {
        feedbackService.removeByLogId(id);
    }

    @ApiOperation(value = "根据 msg id 批量移除日志")
    @RequestMapping("/removeByMsgId")
    @ResponseBody
    public void removeByMsgId(@RequestParam("msgId") String msgId) {
        feedbackService.removeByMsgId(msgId);
    }

    @ApiOperation(value = "根据 rfc name 批量移除日志")
    @RequestMapping("/removeByRfcName")
    @ResponseBody
    public void removeByRfcName(@RequestParam("rfcName") String rfcName) {
        feedbackService.removeByRfcName(rfcName);
    }

    @ApiOperation(value = "根据 log id 查询日志")
    @RequestMapping("/showLogDetail")
    public String selectLogById(@RequestParam("id") long id, ModelMap modelMap) {
        RfcLogFeedback feedback = feedbackService.selectLogById(id);
        if (feedback != null) {
            modelMap.addAttribute("feedback", feedback);
            modelMap.addAttribute("title", feedback.getRfc() + ":" + feedback.getMsgId());
        }
        return "logdetail";
    }

    @ApiOperation(value = "显示所有日志")
    @RequestMapping("/listAll")
    @ResponseBody
    public Object listAll(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "20") int size) {
        return feedbackService.listAll(page, size);
    }

    @ApiOperation(value = "根据 msg id 查询日志")
    @RequestMapping("/selectLogsByMsgId")
    @ResponseBody
    public IPage<RfcLogFeedback> selectLogsByMsgId(@RequestParam("msgId") String msgId) {
        return feedbackService.selectLogsByMsgId(new Page<>(), msgId);
    }

    @ApiOperation(value = "根据 rfc name 查询日志")
    @RequestMapping("/selectLogsByRFCName")
    @ResponseBody
    public IPage<RfcLogFeedback> selectLogsByRFCName(@RequestParam("rfcName") String rfcName) {
        return feedbackService.selectLogsByRFCName(new Page<>(), rfcName);
    }

}
