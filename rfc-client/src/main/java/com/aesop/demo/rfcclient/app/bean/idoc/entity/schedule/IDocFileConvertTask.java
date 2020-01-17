package com.aesop.demo.rfcclient.app.bean.idoc.entity.schedule;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("idoc_file_convert_task")
public class IDocFileConvertTask extends Model<IDocFileConvertTask> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * idoc type
     */
    private String idocType;

    /**
     * directory
     */
    private String directory;

    /**
     * name
     */
    private String name;

    /**
     * path
     */
    private String path;


    /**
     * 是否转换
     */
    private int done;

    /**
     * convert date
     */
    private Date convertDate;

    /**
     * convert time
     */
    private Time convertTime;

    /**
     * convert failure times
     */
    private int convertFailureTimes;

    /**
     * 乐观锁
     */
    @Version
    private long version;

    @Override
    protected Serializable pkVal() {
        return null;
    }


}
