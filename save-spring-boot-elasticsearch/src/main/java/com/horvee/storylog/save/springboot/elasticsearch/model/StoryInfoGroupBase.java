package com.horvee.storylog.save.springboot.elasticsearch.model;


import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.storydetail.ThreadType;

import java.util.List;

public class StoryInfoGroupBase {

    private ThreadType threadType;
    private List<LogInfo> logInfoList;

    public ThreadType getThreadType() {
        return threadType;
    }

    public void setThreadType(ThreadType threadType) {
        this.threadType = threadType;
    }

    public List<LogInfo> getLogInfoList() {
        return logInfoList;
    }

    public void setLogInfoList(List<LogInfo> logInfoList) {
        this.logInfoList = logInfoList;
    }

    @Override
    public String toString() {
        return "StoryInfoBase{" +
                "threadType=" + threadType +
                ", logInfoList=" + logInfoList +
                '}';
    }
}
