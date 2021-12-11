package com.horvee.storylog.core;

import com.horvee.storylog.core.spi.StoryLogSenderBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Try get sender impl
 * */
public class StoryLogSendDriver {

    private static final Logger log = LoggerFactory.getLogger(StoryLogSendDriver.class);
    private static StoryLogSenderBinder storyLogSenderBinder = null;

    static {
        init();
    }

    private static void init() {
        List<StoryLogSenderBinder> storyLogSenderBinderList = new ArrayList<>();
        for (StoryLogSenderBinder storyLogSenderBinder : ServiceLoader.load(StoryLogSenderBinder.class)) {
            storyLogSenderBinderList.add(storyLogSenderBinder);
        }
        if (storyLogSenderBinderList.isEmpty()) {
            log.warn("Can not load anyway 'StoryLogSenderBinder',if you not sender service please ignore this message!");
            return;
        }
        if (storyLogSenderBinderList.size() > 1) {
            log.warn("'StoryLogSenderBinder' load multiple object,object list:[{}]",
                    storyLogSenderBinderList.stream()
                            .map(item -> item.getClass().getName())
                            .collect(Collectors.joining(","))
            );
        }
        log.warn("Will be use 'StoryLogSenderBinder' class:{}",storyLogSenderBinderList.get(0).getClass());
        storyLogSenderBinder = storyLogSenderBinderList.get(0);
    }

    public static StoryLogSender getDrive() {
        if (storyLogSenderBinder == null) {
            throw new RuntimeException("Unable to load 'StoryLogSenderBinder' service");
        }

        return storyLogSenderBinder.isDynamicSender() ? dynamicSender : storyLogSenderBinder.getSender();
    }

    private final static StoryLogSender dynamicSender = (taskInfo, logInfoList) -> {
        storyLogSenderBinder.getSender().send(taskInfo, logInfoList);
    };

}
