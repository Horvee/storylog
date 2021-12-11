package com.horvee.storylog.core;


import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;

/**
 * Log saver
 * Use impl class work by function save log data
 * */
public interface StoryLogSaver {

    /**
     * Save all log to storage (DB,ES,File)
     */
    void saveLog(StoryInfo storyInfo);

}
