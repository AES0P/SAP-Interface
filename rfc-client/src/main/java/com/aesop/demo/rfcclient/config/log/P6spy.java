package com.aesop.demo.rfcclient.config.log;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * P6spy：SQL性能监测
 * <p>
 * 输出SQL语句，以及SQL执行时间
 */
@Component
public class P6spy implements MessageFormattingStrategy {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String s4) {
        return !"".equals(sql.trim()) ? this.format.format(new Date()) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + sql + ";" : "";
    }

}
