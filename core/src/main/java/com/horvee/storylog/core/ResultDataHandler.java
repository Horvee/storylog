package com.horvee.storylog.core;

import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;
import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;

import java.util.Collection;

/**
 * Using to filter and handler metadata (Can be group by log to story log info)
 *
 * */
public interface ResultDataHandler {

    StoryInfo handlerLogData(TaskInfo taskInfo, Collection<LogInfo> logInfoList);

}
