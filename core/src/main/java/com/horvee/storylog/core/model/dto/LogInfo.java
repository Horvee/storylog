package com.horvee.storylog.core.model.dto;

/**
 * A log item info
 * */
public class LogInfo {

    private String threadName;
    private String className;
    private String methodName;// 非必要
    private Integer codeLocation;// 非必要
    private String message;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(Integer codeLocation) {
        this.codeLocation = codeLocation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "threadName='" + threadName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", codeLocation=" + codeLocation +
                ", message='" + message + '\'' +
                '}';
    }
}
