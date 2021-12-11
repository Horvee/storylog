package com.horvee.storylog.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfoGroupBase;
import com.horvee.storylog.core.model.dto.storydetail.ThreadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horvee.storylog.core.model.dto.TaskInfo;

/**
 * Default story log data handler (Use to handler log data and save log data)
 * */
public class DefaultResultDataHandler implements ResultDataHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultResultDataHandler.class);

    /**
     * handler log data function
     *
     * */
    public StoryInfo handlerLogData(TaskInfo taskInfo, Collection<LogInfo> logInfoList) {

        String handlerThread = taskInfo.getOrderThreadName();

        StoryInfo storyInfo = new StoryInfo();

        storyInfo.setId(taskInfo.getId());
        storyInfo.setStoryCode(taskInfo.getStoryCode());
        storyInfo.setStoryTitle(taskInfo.getStoryTitle());
        storyInfo.setStartTime(taskInfo.getStartTime());
        storyInfo.setEndTime(taskInfo.getEndTime());
        storyInfo.setUseTime(taskInfo.getEndTime() - taskInfo.getStartTime());

        List<StoryInfoGroupBase> lastLogList = new ArrayList<>();

        // merge rule
        // same thread name;MainTask、ChildTask;class name match rule

        // Main thread grouping by class name?
        // Child thread grouping by thread name
        // ThreadType: main,child

        // before thread type
        StoryInfoGroupBase tempStoryInfoGroupBase = new StoryInfoGroupBase();// temporary stack,just save a group data (main/child thread)
        for (LogInfo item:logInfoList) {

            if (tempStoryInfoGroupBase.getThreadType() == null) {
                StoryInfoGroupBase storyInfoGroupBase = new StoryInfoGroupBase();
                if (handlerThread.equals(item.getThreadName())) {
                    storyInfoGroupBase.setThreadType(ThreadType.MAIN);
                    storyInfoGroupBase.addLog(item);
                } else {
                    storyInfoGroupBase.setThreadType(ThreadType.CHILD);
                    storyInfoGroupBase.addLog(item);
                }
                tempStoryInfoGroupBase = storyInfoGroupBase;
                continue;
            }

            // first check temporary stack thread is match,unmatched need temporary stack data move in end log stack
            boolean isHandlerThread = handlerThread.equals(item.getThreadName());
            ThreadType logThreadType = isHandlerThread ? ThreadType.MAIN : ThreadType.CHILD;
            if (logThreadType != tempStoryInfoGroupBase.getThreadType()) {
                lastLogList.add(tempStoryInfoGroupBase);
                tempStoryInfoGroupBase = new StoryInfoGroupBase();
                tempStoryInfoGroupBase.setThreadType(logThreadType);
                tempStoryInfoGroupBase.addLog(item);
                continue;
            }

            // check temporary stack thread name is match
            if (handlerThread.equals(item.getThreadName())) {
                // check now class name is equal last log class name! if match add new log to node
                if (Objects.equals(tempStoryInfoGroupBase.getLastLog().getClassName(),item.getClassName())) {
                    tempStoryInfoGroupBase.addLog(item);
                    continue;
                }
            }

            // If not match thread name and class name,then need to be thread type (Main or Child)! analyze data need to merging log

            // Main thread (Is handler request thread)
            if (logThreadType == ThreadType.MAIN) {
                // If match (same time match thread name and class name) then add log to node
                if (Objects.equals(tempStoryInfoGroupBase.getLastLog().getClassName(),item.getClassName())) {
                    tempStoryInfoGroupBase.addLog(item);
                    continue;
                }
                // If not match then create new group and out put new to temporary data
                lastLogList.add(tempStoryInfoGroupBase);
                tempStoryInfoGroupBase = new StoryInfoGroupBase();
                tempStoryInfoGroupBase.setThreadType(logThreadType);
                tempStoryInfoGroupBase.addLog(item);
                //
                continue;
            }

            // task to child thread not be one by one!last log match thread name add to this node,else need create new group
            if (Objects.equals(tempStoryInfoGroupBase.getLastLog().getThreadName(),item.getThreadName())) {
                tempStoryInfoGroupBase.addLog(item);
                continue;
            }

            lastLogList.add(tempStoryInfoGroupBase);
            tempStoryInfoGroupBase = new StoryInfoGroupBase();
            tempStoryInfoGroupBase.setThreadType(logThreadType);
            tempStoryInfoGroupBase.addLog(item);

        }

        // add list from temporary stack data
        lastLogList.add(tempStoryInfoGroupBase);

        storyInfo.setLogList(lastLogList);

        // developer mode enable log print
        if (log.isDebugEnabled()) {
            Printer.simplePrintBaseLog(storyInfo);
        }

        return storyInfo;
    }

}
