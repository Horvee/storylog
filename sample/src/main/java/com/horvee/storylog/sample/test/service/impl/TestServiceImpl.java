package com.horvee.storylog.sample.test.service.impl;

import com.horvee.storylog.core.StoryLogger;
import com.horvee.storylog.sample.test.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TestServiceImpl implements TestService {

    private final static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public void sayHello() {
        log.info("--------start----------");
        StoryLogger.log("Hi,This is a log!");
        StoryLogger.log("Now time {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("---------end-----------");
    }
}
