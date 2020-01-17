package com.aesop.demo.rfcclient.app.mapper.idoc;

import com.aesop.demo.rfcclient.app.bean.idoc.entity.schedule.IDocFileConvertTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IDocScheduleMapper extends BaseMapper<IDocFileConvertTask> {

    @Select("select cron from idoc_schedule limit 1")
    String getCron();

    @Select("select * from idoc_file_convert_task where done = 0")
    List<IDocFileConvertTask> getTaskList();

}
