package com.horvee.storylog.core;


import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;

import java.util.Collection;

/**
 * Log sender
 * Use impl class work by function save log data
 * */
public interface StoryLogSender {

    /**
     * Send log data to (Kafka,MQ,Http)
     * Sender need be run 'tryRemoveToOrderLogLinked' function!while need do this,because sender may be use asynchronous and remove object in send action after
     * @see StoryLogData#tryRemoveToOrderLogLinked(java.lang.String)
     */
    void send(TaskInfo taskInfo, Collection<LogInfo> logInfoList);

}
