package com.horvee.storylog.sample.test.service.impl;

import com.horvee.storylog.core.StoryLogger;
import com.horvee.storylog.sample.test.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestServiceImpl2 implements TestService {

    /**
     * You can use proxy logger function,out put log framework and story log
     * And you can change log level control you code out put to story log content
     * */
    private final static Logger log = StoryLogger.createLog(LoggerFactory.getLogger(TestServiceImpl2.class));

    @Override
    public void sayHello() {
        log.info("--------start----------");
        log.warn("Hi,This is a log!");
        log.warn("Now time {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("---------end-----------");
    }
}
