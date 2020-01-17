package com.aesop.demo.rfcclient.infra.rfc.multitask.threaded;

import com.aesop.demo.rfcclient.infra.rfc.multitask.multijob.MultiStepJob;
import com.aesop.demo.rfcclient.infra.rfc.multitask.multijob.impl.StatelessMultiStep;
import com.sap.conn.jco.JCoFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 本类用于创建多线程任务
 *
 * @author tttttwtt
 */
@Slf4j
@Component
public class MultiThreaded {

    private BlockingQueue<MultiStepJob> queue = new LinkedBlockingQueue<MultiStepJob>();

    // 创建任务与工作线程并拉起
    public void runJobs(JCoFunction function, // JCO连接
                        int jobCount, // job数
                        int threadCount // 工作线程个数
    ) {

        log.info(">>>" + jobCount + "个任务启动");

        for (int i = 0; i < jobCount; i++) {
            queue.add(new StatelessMultiStep(function, 1, i));

        }

        // CountDownLatch同步器的作用是让所有线程都准备好以后 ，真正同时开始执行。
        CountDownLatch startSignal = new CountDownLatch(threadCount);
        CountDownLatch doneSignal = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {

            // threadCount 个工作线程，共同来完成 jobCount个任务
            new WorkerThread(startSignal, doneSignal, queue).start();// 创建并启动工作线程

        }

        log.info(">>>等待执行任务... ");

        try {

            // 主线程等待所有工作任务线程完成后，才结束
            doneSignal.await();
            log.info(">>>完成 \n");

        } catch (InterruptedException ie) {

            ie.printStackTrace();

        }

    }

}
