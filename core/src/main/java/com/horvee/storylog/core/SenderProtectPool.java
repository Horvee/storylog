package com.horvee.storylog.core;

import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Message backlog to be sent! Protect sender pool out of max size,will be use other logic keep server not be down
 *
 * */
public class SenderProtectPool {

    private final static Logger log = LoggerFactory.getLogger(SenderProtectPool.class);
    private final ThreadPoolExecutor threadPoolExecutor;

    public SenderProtectPool(int maximumPoolSize) {
        int corePoolSize = Math.min(maximumPoolSize, Runtime.getRuntime().availableProcessors() * 2);
        threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(maximumPoolSize),
                new LogThreadFactory(),
                rejectedExecutionHandler);
    }

    public SenderProtectPool(int corePoolSize,
                             int maximumPoolSize,
                             long keepAliveTime,
                             TimeUnit unit,
                             BlockingQueue<Runnable> workQueue) {
        threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                new LogThreadFactory(),
                rejectedExecutionHandler);
    }

    /**
     * If run task fail will be print send fail message to log framework
     *
     * */
    public void doSend(TaskInfo taskInfo, Collection<LogInfo> logInfoList, Runnable runnable) {
        threadPoolExecutor.execute(new SenderRunnable(taskInfo,logInfoList,runnable));
    }

    public ExecutorService getExecutorService() {
        return threadPoolExecutor;
    }

    private final static RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {
        if (r instanceof SenderRunnable) {
            SenderRunnable senderRunnable = (SenderRunnable) r;
            log.error("Send story log fail!(task reject) \nTaskInfo:{} \nLogInfoList:{}", senderRunnable.getTaskInfo(), senderRunnable.getLogInfoList());
            return;
        }
        log.error("Do send action fail");
    };

    public static class SenderRunnable implements Runnable {

        private final TaskInfo taskInfo;
        private final Collection<LogInfo> logInfoList;
        private final Runnable logicRunnable;

        public SenderRunnable(TaskInfo taskInfo, Collection<LogInfo> logInfoList, Runnable runnable) {
            this.taskInfo = taskInfo;
            this.logInfoList = logInfoList;
            this.logicRunnable = runnable;
        }

        @Override
        public void run() {
            try {
                logicRunnable.run();
            } catch (Exception e) {
                log.error("Send story log fail! \nTaskInfo:{} \nLogInfoList:{}", getTaskInfo(), getLogInfoList());
                log.error("Send story log fail stack!", e);
            }
        }

        public TaskInfo getTaskInfo() {
            return taskInfo;
        }

        public Collection<LogInfo> getLogInfoList() {
            return logInfoList;
        }
    }

    private static class LogThreadFactory implements ThreadFactory {

        private final AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(MessageFormat.format("logsend-pool-{0}", atomicInteger.incrementAndGet()));
            return thread;
        }
    }

}
