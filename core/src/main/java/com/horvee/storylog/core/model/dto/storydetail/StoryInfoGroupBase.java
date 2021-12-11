package com.horvee.storylog.core.model.dto.storydetail;


import com.horvee.storylog.core.model.dto.LogInfo;

import java.util.ArrayList;
import java.util.List;

/*
* Story log group data
* */
public class StoryInfoGroupBase {

    private ThreadType threadType;
    private final List<LogInfo> logInfoList = new ArrayList<>();

    public ThreadType getThreadType() {
        return threadType;
    }

    public void setThreadType(ThreadType threadType) {
        this.threadType = threadType;
    }

    public void addLog(LogInfo logInfo) {
        logInfoList.add(logInfo);
    }

    public LogInfo getLastLog() {
        return logInfoList.get(logInfoList.size() - 1);
    }

    public List<LogInfo> getLogInfoList() {
        return logInfoList;
    }

}
