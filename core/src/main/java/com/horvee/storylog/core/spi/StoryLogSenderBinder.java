package com.horvee.storylog.core.spi;

import com.horvee.storylog.core.StoryLogSender;

/**
 * Send binder
 * */
public interface StoryLogSenderBinder {

    StoryLogSender getSender();

    /**
     * This status is true,then using any function will be get new sender,keep using every time is new!
     * Something need two step can be initialization component (Sample:base by spring component)
     * */
    default boolean isDynamicSender() {
        return false;
    }

}
