package com.horvee.storylog.core.impl;

import com.horvee.storylog.core.StoryLogSender;
import com.horvee.storylog.core.spi.StoryLogSenderBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StaticStoryLogSenderBinder implements StoryLogSenderBinder {

    private static final Logger log = LoggerFactory.getLogger(StaticStoryLogSenderBinder.class);

    public static StoryLogSenderBinder getSingleton() {
        return new StaticStoryLogSenderBinder();
    }

    @Override
    public StoryLogSender getSender() {
        return (orderInfo, logInfoList) -> {
            log.warn("Use empty log sender impl! (Next simple print message)");
            log.warn(orderInfo.toString());
            log.warn(logInfoList.toString());
        };
    }

}
