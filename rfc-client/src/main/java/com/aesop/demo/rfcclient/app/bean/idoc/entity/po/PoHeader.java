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
@TableName("po_header")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PoHeader extends Model<PoHeader> {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    private long ebeln;

    private String bukrs;

    private String bstyp;

    private String loekz;

    private Date aedat;

    private String ernam;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
