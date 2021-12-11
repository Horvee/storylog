package com.horvee.storylog.sample.test.service.impl;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.horvee.storylog.core.StoryLogger;
import com.horvee.storylog.sample.test.service.TestRealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * real true create business data
 */
@Service
public class TestRealServiceImpl implements TestRealService {

    private final static Logger log = StoryLogger.createLog(LoggerFactory.getLogger(TestRealServiceImpl.class));

    private final ElseService elseService = new ElseServiceImpl();
    private final Random random = new Random();
    private final ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newCachedThreadPool());

    @Override
    public void runBusinessCode() {
        log.info("--------start----------");
        StoryLogger.log("Hi,This is a log!");
        StoryLogger.log("Now time {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        checkParam();
        elseService.doingElseThing();
        String resultContent = runMultipleTaskJob();
        log.info("Service main function get result content:{}", resultContent);
        mergeResultData(resultContent);
        log.info("---------end-----------");
    }

    private void checkParam() {
        int i = (int) (random.nextDouble() * 1000);
        log.warn("doing check param:{}", i);
        log.warn("param is pass:{}", i);
    }

    private void mergeResultData(String resultData) {
        int i = (int) (random.nextDouble() * 1000);
        log.warn("merge data success:{}, resultData:{}", i, resultData);
    }

    interface ElseService {
        void doingElseThing();
    }

    public static class ElseServiceImpl implements ElseService {
        private static final Logger log = StoryLogger.createLog(LoggerFactory.getLogger(ElseServiceImpl.class));

        @Override
        public void doingElseThing() {
            log.warn("doing else thing!");
        }
    }


    private String runMultipleTaskJob() {
        int runThreadTotal = (int) (random.nextDouble() * 10);
        log.warn("run thread total:{}", runThreadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(runThreadTotal);
        List<Integer> dataList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < runThreadTotal; i++) {
            int finalI = i;
            executorService.execute(() -> {
                log.warn("Ha ha im get new job!");
                log.warn("The job number is:{}", finalI);
                int newValue = (int) (random.nextDouble() * 10);
                dataList.add(newValue);
                log.warn("I'm create new value:{}", newValue);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("Run fail!", e);
        }

        String result = Arrays.toString(dataList.toArray());
        log.warn("The result value is:{}", result);
        return result;
    }
}
