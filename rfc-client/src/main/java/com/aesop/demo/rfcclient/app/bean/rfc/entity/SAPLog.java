package com.aesop.demo.rfcclient.app.bean.rfc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 这个用来和SAP做 DB connect 用，将SAP的日志表数据全量同步到本项目中
 */
@Data
public class SAPLog extends Model<SAPLog> {

    /**
     * 客户端
     */
    private int MANDT;

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long IFSNR;

    /**
     * 日志行号
     */
    private long IFPOS;

    /**
     * 接口编号
     */
    private String ZIFNO;

    /**
     * 描述
     */
    private String ZIFDS;

    /**
     * 业务关键字
     */
    private String ZCKEY;

    /**
     * 请求时间戳
     */
    private Timestamp REQDT;

    /**
     * 发送方
     */
    private String SENDR;

    /**
     * 接收方
     */
    private String RECER;

    /**
     * 请求日期
     */
    private Date REQDE;

    /**
     * 请求时间
     */
    private Time REQTE;

    /**
     * 返回日期
     */
    private Date REPDE;

    /**
     * 返回时间
     */
    private Time REPTE;

    /**
     * 消息标识
     */
    private String MSGID;

    /**
     * 消息执行状态：S/E/W/I/A
     */
    private String IFFLG;

    /**
     * 说明
     */
    private String IFMSG;

    /**
     * 用户名
     */
    private String USNAM;

    /**
     * 事务代码
     */
    private String TCODE;

    /**
     * ABAP 程序名称
     */
    private String REPID;

    /**
     * 预留/备注
     */
    private String ZNOTE1;

    /**
     * 预留/备注
     */
    private String ZNOTE2;

    /**
     * 预留/备注
     */
    private String ZNOTE3;

    /**
     * 预留/备注
     */
    private String ZNOTE4;

    /**
     * 预留/备注
     */
    private String ZNOTE5;

    /**
     * 预留/备注
     */
    private String ZNOTE6;

    /**
     * 预留/备注
     */
    private String ZNOTE7;

    /**
     * 预留/备注
     */
    private String ZNOTE8;

    /**
     * 预留/备注
     */
    private String ZNOTE9;

    /**
     * 预留/备注
     */
    private String ZNOTe10;

    /**
     * 预留/备注
     */
    private String ZNOTE11;

    /**
     * 预留/备注
     */
    private String ZNOTE12;

    /**
     * 预留/备注
     */
    private String ZNOTE13;

    /**
     * 预留/备注
     */
    private String ZNOTE14;

    /**
     * 预留/备注
     */
    private String ZNOTE15;

}
