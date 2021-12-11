package com.horvee.storylog.save.springboot.elasticsearch;

import com.horvee.storylog.core.StoryLogSaver;
import com.horvee.storylog.core.spi.StoryLogSaverBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringBootElasticSearchStoryLogSaveBinder implements StoryLogSaverBinder {

    private final static Logger log = LoggerFactory.getLogger(SpringBootElasticSearchStoryLogSaveBinder.class);

    private static volatile boolean initState = false;
    private static StoryLogSaver saver = (storyAllInfo) -> {
        log.warn("StoryLogSaveComponent not be init");
    };

    @Override
    public StoryLogSaver getSaver() {
        return saver;
    }

    protected static void registerService(StoryLogSaver saverBean) {
        initState = true;
        saver = saverBean;
    }

    public static boolean getInitState() {
        return initState;
    }


    @Override
    public boolean isDynamicSaver() {
        return true;
    }
}
