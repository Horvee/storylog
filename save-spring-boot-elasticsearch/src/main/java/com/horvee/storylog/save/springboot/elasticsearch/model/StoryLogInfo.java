package com.horvee.storylog.save.springboot.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;


@Document(indexName = "#{storyLogElasticSearchProperties.indexName}")
public class StoryLogInfo {

    @Id
    private String id;

    private Integer storyCode;

    @Field(analyzer = "#{storyLogElasticSearchProperties.storyTitleAnalyzer}", searchAnalyzer = "#{storyLogElasticSearchProperties.storyTitleSearchAnalyzer}")
    private String storyTitle;

    @Field(type = FieldType.Date)
    private Long startTime;

    @Field(type = FieldType.Date)
    private Long endTime;

    @Field(type = FieldType.Long)
    private Long useTime;

    @Field(type = FieldType.Object, analyzer = "#{storyLogElasticSearchProperties.logContentAnalyzer}", searchAnalyzer = "#{storyLogElasticSearchProperties.logContentSearchAnalyzer}")
    private List<StoryInfoGroupBase> logContent;

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

    public List<StoryInfoGroupBase> getLogContent() {
        return logContent;
    }

    public void setLogContent(List<StoryInfoGroupBase> logContent) {
        this.logContent = logContent;
    }

    @Override
    public String toString() {
        return "StoryLogInfo{" +
                "id='" + id + '\'' +
                ", storyCode=" + storyCode +
                ", storyTitle='" + storyTitle + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", useTime=" + useTime +
                ", logContent=" + logContent +
                '}';
    }
}
