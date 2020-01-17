package com.aesop.demo.rfcclient.infra.idoc.util;

import com.aesop.demo.rfcclient.app.bean.idoc.entity.schedule.IDocFileConvertTask;
import com.aesop.demo.rfcclient.app.service.idoc.IDocScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Configuration
@EnableScheduling
public class IDocScheduleConfig implements SchedulingConfigurer {

    @Autowired
    private IDocScheduleService iDocScheduleService;

    @Autowired
    private IDocFileHandler iDocFileHandler;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(

                //1.添加任务内容(Runnable)
                this::task,

                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = iDocScheduleService.getCron();
                    log.info("\r\ncron:" + cron);
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        cron = "0/30 * * * * ?";// 30 S 一次
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }

        );

    }


    private void task() {

        log.info("\r\n执行IDOC文件转换定时任务: " + LocalDateTime.now().toLocalTime());

        int count = 0;

        List<IDocFileConvertTask> convertTaskLists = iDocScheduleService.getTaskList();

        if (!convertTaskLists.isEmpty()) {

            ListIterator li = convertTaskLists.listIterator();
            while (li.hasNext()) {

                IDocFileConvertTask task = (IDocFileConvertTask) li.next();

//                task.setVersion(task.getVersion() + 1);
                task.setConvertDate(new java.sql.Date(System.currentTimeMillis()));
                task.setConvertTime(new Time(System.currentTimeMillis()));

                if (iDocFileHandler.autoConvertObjectAndRecord(task.getPath(), task.getIdocType())) {
                    task.setDone(1);
                    count++;
                } else {
                    task.setDone(0);
                    task.setConvertFailureTimes(task.getConvertFailureTimes() + 1);
                }

                li.set(task);

            }
            log.info("\r\n" + count + " file(s) convert!");
            iDocScheduleService.batchUpdate(convertTaskLists);
        } else {
            log.info("\r\nIDoc convert task list is empty...");
        }

    }

}
