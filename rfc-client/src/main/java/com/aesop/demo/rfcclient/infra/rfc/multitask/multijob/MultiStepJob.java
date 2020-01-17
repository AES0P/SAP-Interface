package com.aesop.demo.rfcclient.infra.rfc.multitask.multijob;

/**
 * 多线程任务接口定义
 * 在StatelessMultiStep类中实现
 *
 * @author tttttwtt
 */
public interface MultiStepJob {

    String getName();// 任务名

    boolean isFinished();// 任务是否完成

    void runNextStep();// 运行任务

    void cleanUp();// 清除任务

}
