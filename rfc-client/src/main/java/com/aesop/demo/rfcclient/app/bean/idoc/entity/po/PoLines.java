package com.aesop.demo.rfcclient.app.bean.idoc.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Aesop
 * @since 2020-01-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("po_lines")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PoLines extends Model<PoLines> {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    private long ebeln;

    private Integer ebelp;

    private String loekz;

    private Date aedat;

    private String txz01;

    private String bukrs;

    private String werks;

    private String lgort;

    private Float menge;

    private String meins;

    private String bprme;

    private Integer bpumz;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
