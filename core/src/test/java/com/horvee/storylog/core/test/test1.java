package com.horvee.storylog.core.test;

import com.horvee.storylog.core.StoryLogger;

import java.util.Arrays;

import org.junit.Test;

import com.horvee.storylog.core.model.dto.TaskInfo;

public class test1 {

    @Test
    public void t1() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId("123123123");
        taskInfo.setStoryTitle("test");
//    StoryLogData.createThreadInfo(orderInfo);

//    System.out.println(StoryLogData.getParamOrderCode());
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
    }

    @Test
    public void t2() {
        System.out.println(StoryLogger.margeMessage("123{},{}end,{}"));
        System.out.println(StoryLogger.margeMessage("123{},{}end,{}", "456"));
//    System.out.println(StoryLogger.margeMessage("123{},{}","456"));

//    long start = System.currentTimeMillis();
//    StackTraceElement[] aa = Thread.currentThread().getStackTrace();
//    System.out.println(System.currentTimeMillis() - start);
        deepFunctionTest(200, 0);
    }

    private void deepFunctionTest(int deep, int now) {
        if (deep == now) {
            long start = System.currentTimeMillis();
            StackTraceElement[] aa = Thread.currentThread().getStackTrace();
            System.out.println(System.currentTimeMillis() - start + "---" + aa.length);
            return;
        }
        now++;
        deepFunctionTest(deep, now);
    }

    @Test
    public void t3() {
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t4() {
        String text = "123.";
        System.out.println(text.substring(text.lastIndexOf(".") + 1));

        String fileSuffix = "";
        if (text == null) {
            fileSuffix = "";
        } else {
            int lastFormatIndexOf = text.lastIndexOf(".");
            fileSuffix = (lastFormatIndexOf <= -1 || (lastFormatIndexOf + 1) == text.length()) ?
                    "" : text.substring(lastFormatIndexOf);
        }
        System.out.println("'" + fileSuffix + "'");
    }


}
