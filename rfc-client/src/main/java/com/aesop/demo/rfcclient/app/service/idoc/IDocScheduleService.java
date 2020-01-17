package com.aesop.demo.rfcclient.app.service.idoc;

import com.aesop.demo.rfcclient.app.bean.idoc.entity.schedule.IDocFileConvertTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IDocScheduleService extends IService<IDocFileConvertTask> {

    void saveTask(IDocFileConvertTask task);

    void batchUpdate(List<IDocFileConvertTask> iDocFileConvertTask);

    String getCron();

    List<IDocFileConvertTask> getTaskList();


}
