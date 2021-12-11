package com.horvee.storylog.core.model.dto.storydetail;

import java.util.List;

/**
 * story log once task info
 * */
public class StoryInfo {

    private String id;
    private Integer storyCode;
    private String storyTitle;
    private Long startTime;
    private Long endTime;
    private Long useTime;
    private List<StoryInfoGroupBase> logList;

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

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public List<StoryInfoGroupBase> getLogList() {
        return logList;
    }

    public void setLogList(List<StoryInfoGroupBase> logList) {
        this.logList = logList;
    }

    @Override
    public String toString() {
        return "StoryAllInfo{" +
                "id='" + id + '\'' +
                ", storyCode=" + storyCode +
                ", storyTitle='" + storyTitle + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", useTime=" + useTime +
                ", logList=" + logList +
                '}';
    }
}
