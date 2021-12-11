package com.horvee.storylog.core;

import com.horvee.storylog.core.spi.StoryLogSaverBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * Try get saver impl
 * */
public class StoryLogSaverDriver {

    private static final Logger log = LoggerFactory.getLogger(StoryLogSaverDriver.class);
    private static StoryLogSaverBinder storyLogSaverBinder = null;

    static {
        init();
    }

    private static void init() {
        List<StoryLogSaverBinder> storyLogSaverBinders = new ArrayList<>();
        for (StoryLogSaverBinder storyLogSaverBinder : ServiceLoader.load(StoryLogSaverBinder.class)) {
            storyLogSaverBinders.add(storyLogSaverBinder);
        }
        if (storyLogSaverBinders.isEmpty()) {
            log.warn("Can not load anyway 'StoryLogSaverBinder',if you not saver service please ignore this message!");
            return;
        }
        if (storyLogSaverBinders.size() > 1) {
            log.warn("'StoryLogSenderBinder' load multiple object,object list:[{}]",
                    storyLogSaverBinders.stream()
                            .map(item -> item.getClass().getName())
                            .collect(Collectors.joining(","))
            );
        }
        log.warn("Will be use 'StoryLogSaverBinder' class:{}",storyLogSaverBinders.get(0).getClass());
        storyLogSaverBinder = storyLogSaverBinders.get(0);
    }

    public static StoryLogSaver getDrive() {
        if (storyLogSaverBinder == null) {
            throw new RuntimeException("Unable to load 'StoryLogSaverBinder' service");
        }
        return storyLogSaverBinder.isDynamicSaver() ? dynamicSaver : storyLogSaverBinder.getSaver();
    }

    private final static StoryLogSaver dynamicSaver = (storyInfo) -> {
        storyLogSaverBinder.getSaver().saveLog(storyInfo);
    };

}
