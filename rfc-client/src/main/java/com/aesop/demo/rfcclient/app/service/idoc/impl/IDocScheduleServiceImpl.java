package com.aesop.demo.rfcclient.app.service.idoc.impl;

import com.aesop.demo.rfcclient.app.bean.idoc.entity.schedule.IDocFileConvertTask;
import com.aesop.demo.rfcclient.app.mapper.idoc.IDocScheduleMapper;
import com.aesop.demo.rfcclient.app.service.idoc.IDocScheduleService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@DS("master")
public class IDocScheduleServiceImpl extends ServiceImpl<IDocScheduleMapper, IDocFileConvertTask> implements IDocScheduleService {

    @Autowired
    private IDocScheduleMapper scheduleMapper;

    @Override
    public void saveTask(IDocFileConvertTask task) {
        save(task);
    }

    @Override
    public void batchUpdate(List<IDocFileConvertTask> iDocFileConvertTask) {
        updateBatchById(iDocFileConvertTask);
    }

    @Override
    @DS("slave_1")
    public String getCron() {
        return scheduleMapper.getCron();
    }

    @Override
    @DS("slave_2")
    public List<IDocFileConvertTask> getTaskList() {
        return scheduleMapper.getTaskList();
    }


}
