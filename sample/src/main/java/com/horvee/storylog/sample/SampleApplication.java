package com.horvee.storylog.sample;

import com.horvee.storylog.springboot.configuration.annotation.EnableStoryLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot project must be use 'EnableStoryLog' annotation
 *
 * */
@EnableStoryLog
@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
//        StoryLogSender sender = StoryLogSendDriver.getDrive();
//        new Thread(() -> {
//            for (;;) {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////                System.out.println("senderTargetClass:" + sender.getClass().getName());
////                sender.send(new TaskInfo(),new ArrayList<>());
//
//                StoryLogSender senderr = StoryLogSendDriver.getDrive();
//                System.out.println("senderTargetClass:" + senderr.getClass().getName());
//                senderr.send(new TaskInfo(),new ArrayList<>());
//            }
//        }).start();
        SpringApplication.run(SampleApplication.class,args);
    }

}
