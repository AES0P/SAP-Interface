package com.aesop.demo.rfcclient.app.bean.rfc.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Aesop
 * @since 2020-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rfc_log_feedback")
@AllArgsConstructor
@NoArgsConstructor
public class RfcLogFeedback extends Model<RfcLogFeedback> {

    /**
     * log id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 消息标识 sap 自动生成
     */
    private String msgId;

    /**
     * RFC名称
     */
    private String rfc;

    /**
     * 调用人 ID
     */
    @TableField(fill = FieldFill.INSERT)
    private String userId;

    /**
     * 调用人 名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String userName;

    /**
     * sap 反馈信息 关键字
     */
    private String dataKey;

    /**
     * sap 反馈信息 信息标识
     */
    private String flag;

    /**
     * sap 反馈信息 长文本
     */
    private String message;

    /**
     * 时间戳
     */
    @TableField(fill = FieldFill.INSERT)
    private long timestamp;

    /**
     * 乐观锁
     */
    @Version
    private long version;

    public RfcLogFeedback(String msgId, String rfcName, String dataKey, String flag, String message) {
        this.msgId = msgId;
        this.rfc = rfcName;
        this.dataKey = dataKey;
        this.flag = flag;
        this.message = message;
    }


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
