package com.aesop.demo.rfcclient.app.service.rfc;

import com.aesop.demo.rfcclient.app.bean.rfc.entity.RfcLogFeedback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Aesop
 * @since 2020-01-07
 */
public interface RfcLogFeedbackService extends IService<RfcLogFeedback> {

    RfcLogFeedback insert(RfcLogFeedback feedback);

    RfcLogFeedback update(RfcLogFeedback feedback);

    void batchInsertOrUpdate(List<RfcLogFeedback> feedbackList);

    void removeByLogId(long id);

    void removeByMsgId(String msgId);

    void removeByRfcName(String rfcName);

    RfcLogFeedback selectLogById(long id);

    Object listAll(int page, int size);

    IPage<RfcLogFeedback> selectLogsByMsgId(Page<RfcLogFeedback> page, String msgId);

    IPage<RfcLogFeedback> selectLogsByRFCName(Page<RfcLogFeedback> page, String rfcName);

}
