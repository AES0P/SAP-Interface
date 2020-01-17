package com.aesop.demo.rfcclient.infra.rfc.multitask.threaded;

import com.aesop.demo.rfcclient.infra.rfc.multitask.multijob.MultiStepJob;
import com.aesop.demo.rfcclient.infra.rfc.multitask.session.MySessionReference;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

//工作线程，用来执行RFC
@Slf4j
@Component
@NoArgsConstructor
public class WorkerThread extends Thread {

    // 开始阀：所有线程都已启动并就绪时，所有线程不再阻塞
    private CountDownLatch startSignal;

    // 结束阀：所以线程结束后，主线程才结束
    private CountDownLatch doneSignal;

    private BlockingQueue<MultiStepJob> queue = new LinkedBlockingQueue<MultiStepJob>();

    // 任务与远程会话映射关系表：确保同一任务要在同一远程会话中执行
    public Hashtable<MultiStepJob, MySessionReference> sessions = new Hashtable<MultiStepJob, MySessionReference>();

    // ThreadLocal：线程全局变量局部化，即将原本共享的属性全局变量在每个线程中都拷贝一份，不会让它们再在不同的线程中共享，
    // 每个线程拿到的都是自己所独享的，所以看似全局共享的属性在多线程情况下，也不会出现多线程并发问题

    // 当前线程所使用的远程会话
    public ThreadLocal<MySessionReference> localSessionReference = new ThreadLocal<MySessionReference>();

    // 同步器：倒计时闭锁；threadCount为倒计数值，直到该数为0时，await()才会结束继续往下执行

    /**
     * CountDownLatch同步器的作用是让所有线程都准备好以后 ，真正同时开始执行。
     * 这样不会因为先创建的的线程就会先执行，可以真正模拟多线程同时执行的情况 这样在研究多线程在访问同一临界资源时，容易发现线程并发问题
     *
     * @param startSignal
     * @param doneSignal
     * @param queue
     */
    public WorkerThread(CountDownLatch startSignal, CountDownLatch doneSignal, BlockingQueue<MultiStepJob> queue) {

        this.startSignal = startSignal;

        this.doneSignal = doneSignal;

        this.queue = queue;

    }

    // 工作线程
    public void run() {

        startSignal.countDown();

        try {

            // 所有线程都已经运行到这里后，才开始一起同时向下执行，否则一直阻塞
            startSignal.await();

            // 某一时间段内（即一次循环）只执行某个任务的一个步骤

            for (; ; ) {// 直到任务队列中没有任务时退出

                // 出队，工作线程从任务队列中取任务：如果等10秒都未取到，则返回NULL
                MultiStepJob job = queue.poll(10, TimeUnit.SECONDS);

                // stop if nothing to do
                if (job == null) {// 如果任务队列中没有任务后，工作线程将退出

                    return;

                }

                // 取任务所对应的远程会话，确保每个任务使用同一远程会话
                MySessionReference sesRef = sessions.get(job);

                if (sesRef == null) {// 如果是第一次，则新创建一个远程会话，再将任务与该会话进行绑定

                    sesRef = new MySessionReference();

                    sessions.put(job, sesRef);

                }

                // 存储当前线程所使用的远程会话。该值的读取是在调用远程RFM前，由JCo框架的
                // SessionReferenceProvider的getCurrentSessionReference()方法来读取
                // ****不管是否有无状态RFM调用，最好都要确保同一任务（多个RFM所组成的远程调用任务）在同一会话中执行
                // ****，要做到这一点，在Java端需要保证不同线程（同一线程也是）在执行同一任务时，远程会话要是同一个
                // 注：同一任务需要设置为同一远程会话，不同任务不能设置为相同的远程会话，否则计数器会在多个任务中共用

                localSessionReference.set(sesRef);

                log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "任务 "
                        + job.getName() + " 开始执行.");

                try {

                    // 执行任务

                    job.runNextStep();

                } catch (Throwable th) {

                    th.printStackTrace();

                }

                // 如果任务完成
                if (job.isFinished()) {

                    // 如果任务执行完了，则从映射表是删除任务与远程会话映射记录
                    sessions.remove(job);

                    job.cleanUp();// 任务的所有步骤执行完后，输出任务结果

                } else {

                    // log.info("任务 " + job.getName() + "
                    // 暂未完成，重新放入任务队列，等待下次继续执行.");

                    // 如果发现任务还没有执行完，则重新放入任务队列中，等待下一次继续执行。从这里可以看出

                    // 计数器的增加与读取可能是由不同的工作线程来完成的，但要确保同一任务是在同一远程会话中调用的

                    queue.add(job);

                }

                // 当某个任务某一步执行完后，清除当前线程所存储的远程会话。注：这里的工作线程某一时间段内（即一次循环内）只能执行一个任务

                localSessionReference.set(null);

            }

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            doneSignal.countDown();

        }

    }

}
