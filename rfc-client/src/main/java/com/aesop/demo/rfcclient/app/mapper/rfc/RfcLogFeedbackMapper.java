package com.aesop.demo.rfcclient.app.mapper.rfc;

import com.aesop.demo.rfcclient.app.bean.rfc.entity.RfcLogFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Aesop
 * @since 2020-01-07
 */
@Component
@Mapper
public interface RfcLogFeedbackMapper extends BaseMapper<RfcLogFeedback> {

    IPage<RfcLogFeedback> selectLogsByMsgId(Page<RfcLogFeedback> page, @Param("msgId") String msgId);//通过mapper.xml方式

    @Select("select * from rfc_log_feedback where rfc=#{rfcName}")
    IPage<RfcLogFeedback> selectUsersByRFCName(Page<RfcLogFeedback> page, @Param("rfcName") String rfcName);//通过注解方式

    @Delete("delete from rfc_log_feedback where msg_id=#{msgId}")
    void deleteByMsgId(@Param("msgId") String msgId);

    @Delete("delete from rfc_log_feedback where rfc=#{rfcName}")
    void deleteByRfcName(@Param("rfcName") String rfcName);
}
