package com.horvee.storylog.springboot.configuration.config.saver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storylog.save.elastic-search")
public class StoryLogElasticSearchProperties {

    /**
     * save document index name
     * */
    private String indexName = "storylog";

    /**
     * Field analyzer and search analyzer config
     * */
    private String storyTitleAnalyzer = "";
    private String storyTitleSearchAnalyzer = "";

    private String logContentAnalyzer = "";
    private String logContentSearchAnalyzer = "";

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getStoryTitleAnalyzer() {
        return storyTitleAnalyzer;
    }

    public void setStoryTitleAnalyzer(String storyTitleAnalyzer) {
        this.storyTitleAnalyzer = storyTitleAnalyzer;
    }

    public String getStoryTitleSearchAnalyzer() {
        return storyTitleSearchAnalyzer;
    }

    public void setStoryTitleSearchAnalyzer(String storyTitleSearchAnalyzer) {
        this.storyTitleSearchAnalyzer = storyTitleSearchAnalyzer;
    }

    public String getLogContentAnalyzer() {
        return logContentAnalyzer;
    }

    public void setLogContentAnalyzer(String logContentAnalyzer) {
        this.logContentAnalyzer = logContentAnalyzer;
    }

    public String getLogContentSearchAnalyzer() {
        return logContentSearchAnalyzer;
    }

    public void setLogContentSearchAnalyzer(String logContentSearchAnalyzer) {
        this.logContentSearchAnalyzer = logContentSearchAnalyzer;
    }

    @Override
    public String toString() {
        return "ElasticSearch{" +
                "indexName='" + indexName + '\'' +
                ", storyTitleAnalyzer='" + storyTitleAnalyzer + '\'' +
                ", storyTitleSearchAnalyzer='" + storyTitleSearchAnalyzer + '\'' +
                ", logContentAnalyzer='" + logContentAnalyzer + '\'' +
                ", logContentSearchAnalyzer='" + logContentSearchAnalyzer + '\'' +
                '}';
    }
}
