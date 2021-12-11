package com.horvee.storylog.core.model.dto;

/**
 * Executor function task info
 * */
public class TaskInfo {

    private String id;
    private Integer storyCode;
    private String storyTitle;
    private String topCallId;// top call stack id
    private String parentCallId;// after call this task id
    private String orderThreadName;
    private Long startTime;
    private Long endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStoryCode() {
        return storyCode;
    }

    public void setStoryCode(Integer storyCode) {
        this.storyCode = storyCode;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getOrderThreadName() {
        return orderThreadName;
    }

    public void setOrderThreadName(String orderThreadName) {
        this.orderThreadName = orderThreadName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTopCallId() {
        return topCallId;
    }

    public void setTopCallId(String topCallId) {
        this.topCallId = topCallId;
    }

    public String getParentCallId() {
        return parentCallId;
    }

    public void setParentCallId(String parentCallId) {
        this.parentCallId = parentCallId;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "id='" + id + '\'' +
                ", storyCode=" + storyCode +
                ", storyTitle='" + storyTitle + '\'' +
                ", topCallId='" + topCallId + '\'' +
                ", parentCallId='" + parentCallId + '\'' +
                ", orderThreadName='" + orderThreadName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
