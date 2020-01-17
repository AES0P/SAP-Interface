package com.aesop.demo.rfcclient.infra.rfc.multitask.multijob.impl;

import com.aesop.demo.rfcclient.infra.rfc.multitask.multijob.MultiStepJob;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 此类实现了MultiStepJob接口 用于指定线程将要执行的任务，主要逻辑在runNextStep方法中，执行了RFC
 *
 * @author tttttwtt
 */
@Slf4j
@Service
@NoArgsConstructor
public class StatelessMultiStep implements MultiStepJob {

    @Resource
    private JCoDestination jCoDestination;

    private JCoFunction function;

    private int calls;// 调用次数

    private int jobID = 0;// 任务编号

    private int executedCalls = 0;// 记录调用次数，即任务步骤

    private Exception ex = null;// 记录任务执行过程出现的异常

    // private int remoteCounter;// 计数结果

    public StatelessMultiStep(JCoFunction function, int calls, int jobID) {

        this.function = function;

        this.calls = calls;

        this.jobID = jobID;

    }

    public boolean isFinished() {

        // 如果已经调用完，或者调用过程中出现了异常，表示任务已完成
        return executedCalls == calls || ex != null;

    }

    public String getName() {// 任务名

        return "Job ID:" + jobID;

    }

    // 任务的某一步，究竟有多少步则外界来传递进来的calls变量来控制
    public void runNextStep() {

        try {

            // 注：在调用远程RFC功能函数之前，JCo框架会去调用SessionReferenceProvider的getCurrentSessionReference()方法
            // 取得当前任务所对应的远程会话，确保同一任务是在同一远程会话中执行的
            function.execute(jCoDestination);

            executedCalls++;//记录第几次调用

        } catch (JCoException je) {

            ex = je;

        }

    }

    public void cleanUp() {// 任务结束后，清除任务

        StringBuilder sb = new StringBuilder("任务 ").append(getName()).append(" 结束：");

        if (ex != null) {

            sb.append("异常结束 ").append(ex.toString());

        } else {

            // sb.append("成功执行完，计数器值 = ").append(remoteCounter);
            sb.append("成功执行完毕！ ");

        }

        log.info(sb.toString());

    }

}
