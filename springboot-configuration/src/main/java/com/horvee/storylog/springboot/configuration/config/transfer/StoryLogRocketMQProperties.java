package com.horvee.storylog.springboot.configuration.config.transfer;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storylog.transfer.rocketmq")
public class StoryLogRocketMQProperties {

    /**
     * story log default topic is 'story-log',you can custom other value
     */
    private String topic = "story-log";

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "StoryLogRocketMQProperties{" +
                "topic='" + topic + '\'' +
                '}';
    }
}
