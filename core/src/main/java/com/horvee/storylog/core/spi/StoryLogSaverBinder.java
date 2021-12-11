package com.horvee.storylog.core.spi;

import com.horvee.storylog.core.StoryLogSaver;

/**
 * Save binder
 * */
public interface StoryLogSaverBinder {

    StoryLogSaver getSaver();

    /**
     * This status is true,then using any function will be get new sender,keep using every time is new!
     * Something need two step can be initialization component (Sample:base by spring component)
     * */
    default boolean isDynamicSaver() {
        return false;
    }

}
