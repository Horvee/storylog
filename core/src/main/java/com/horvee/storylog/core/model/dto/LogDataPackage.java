package com.horvee.storylog.core.model.dto;

import java.util.Collection;

/**
 * Package will be save data dto
 *
 * */
public class LogDataPackage {

    private TaskInfo taskInfo;

    private Collection<LogInfo> logInfoList;

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Collection<LogInfo> getLogInfoList() {
        return logInfoList;
    }

    public void setLogInfoList(Collection<LogInfo> logInfoList) {
        this.logInfoList = logInfoList;
    }

    public static final class LogDataPackageBuilder {
        private TaskInfo taskInfo;
        private Collection<LogInfo> logInfoList;

        public LogDataPackageBuilder() {
        }

        public static LogDataPackageBuilder aLogDataPackage() {
            return new LogDataPackageBuilder();
        }

        public LogDataPackageBuilder orderInfo(TaskInfo taskInfo) {
            this.taskInfo = taskInfo;
            return this;
        }

        public LogDataPackageBuilder logInfoList(Collection<LogInfo> logInfoList) {
            this.logInfoList = logInfoList;
            return this;
        }

        public LogDataPackage build() {
            LogDataPackage logDataPackage = new LogDataPackage();
            logDataPackage.logInfoList = this.logInfoList;
            logDataPackage.taskInfo = this.taskInfo;
            return logDataPackage;
        }
    }
}
