package com.horvee.storylog.core.impl;

import com.horvee.storylog.core.StoryLogSaver;
import com.horvee.storylog.core.spi.StoryLogSaverBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StaticStoryLogSaverBinder implements StoryLogSaverBinder {

    private static final Logger log = LoggerFactory.getLogger(StaticStoryLogSaverBinder.class);

    public static StoryLogSaverBinder getSingleton() {
        return new StaticStoryLogSaverBinder();
    }

    @Override
    public StoryLogSaver getSaver() {
        return (storyAllInfo) -> {
            log.warn("Use empty log saver impl! (Next simple print message)");
            log.warn(storyAllInfo.toString());
        };
    }
}
